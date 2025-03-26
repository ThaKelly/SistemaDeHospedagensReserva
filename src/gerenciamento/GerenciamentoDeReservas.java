package gerenciamento;

import entidades.Reserva;
import entidades.Cliente;
import entidades.Hospedagem;
import entidades.ServicoAdicional;
import repositorio.RepositorioDoHotel;
import exception.HospedagemIndisponivelException;
import exception.ReservaNaoEncontradaException;
import exception.ServicoNaoPermitidoException;
import exception.CancelamentoForaDoPrazoException;
import exception.CheckInInvalidoException;
import exception.CheckOutInvalidoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciamentoDeReservas {
	
	private RepositorioDoHotel hotelRepository;

    public GerenciamentoDeReservas( RepositorioDoHotel hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public void realizarReserva(Cliente cliente, Hospedagem hospedagem, String idReserva) throws HospedagemIndisponivelException {
        if (!hospedagem.isDisponivel()) {
            throw new HospedagemIndisponivelException("A hospedagem não está disponível.");
        }
        Reserva novaReserva = new Reserva(idReserva, cliente, hospedagem, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5), "ativa");
        hotelRepository.addReserva(novaReserva);
        hospedagem.reservar(); 
        System.out.println("Nova reserva de hospedagem realizada com sucesso.");
    }

    public void adicionarServicoAdicional(Cliente cliente, ServicoAdicional servicoAdicional) 
            throws ReservaNaoEncontradaException, ServicoNaoPermitidoException {
        Reserva reservaExistente = buscarReservaAtivaPorCliente(cliente);
        
        if (reservaExistente == null) {
            throw new ReservaNaoEncontradaException("Cliente não possui uma reserva ativa.");
        }       
        if (!servicoAdicional.isDisponivel()) {
            throw new ServicoNaoPermitidoException("Serviço adicional " + servicoAdicional.getDescricao() + " não está disponível.");
        }
        reservaExistente.adicionarServicoAdicional(servicoAdicional);
        servicoAdicional.reservar();
    }

    private Reserva buscarReservaAtivaPorCliente(Cliente cliente) {
        for (Reserva reserva : hotelRepository.getReservas()) {
        	if (reserva.getCliente().equals(cliente) && 
            (reserva.getStatus().equals("Ativa") || reserva.getStatus().equals("Em andamento")))   {
                return reserva;
            }
        }
        return null; 
    }

    public boolean cancelarReserva(String idReserva, String motivoCancelamento) 
            throws ReservaNaoEncontradaException, CancelamentoForaDoPrazoException {          
        Reserva reserva = buscarReservaPorId(idReserva);              
        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada com o ID: " + idReserva);
        }
        boolean cancelada = reserva.cancelarReserva(24); // 24 horas antes do check-in
        if (cancelada) {           
            reserva.setMotivoCancelamento(motivoCancelamento);         
            reserva.getHospedagem().liberar();           
            return true;
        } else {
            throw new CancelamentoForaDoPrazoException("Cancelamento não permitido. O prazo é até 24 horas antes do check-in.");
        }
    }

    public Reserva buscarReservaPorId(String idReserva) throws ReservaNaoEncontradaException {
        for (Reserva reserva : hotelRepository.getReservas()) {
            if (reserva.getId().equals(idReserva)) {
                return reserva;
            }
        }
        throw new ReservaNaoEncontradaException("Reserva não encontrada com o ID: " + idReserva);
    }

    public List<Reserva> listarReservas() {
        return hotelRepository.getReservas(); 
    }

    public void realizarCheckIn(String idReserva) throws ReservaNaoEncontradaException, CheckInInvalidoException {     
        Reserva reserva = buscarReservaPorId(idReserva);     
        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada com o ID: " + idReserva);
        }
        if (!reserva.getStatus().equals("Ativa")) {
            throw new CheckInInvalidoException("Check-in não permitido. A reserva não está ativa.");
        }   
        LocalDateTime agora = LocalDateTime.now();
        if (agora.isBefore(reserva.getDataCheckIn().withHour(14).withMinute(0).withSecond(0))) {
            throw new CheckInInvalidoException("Check-in não permitido antes das 14h do dia do check-in.");
        }     
        reserva.setStatus("Em andamento");  
    
    }
    
    public void realizarCheckOut(String idReserva) throws ReservaNaoEncontradaException, CheckOutInvalidoException {      
        Reserva reserva = buscarReservaPorId(idReserva);       
        if (reserva == null) {
            throw new ReservaNaoEncontradaException("Reserva não encontrada com o ID: " + idReserva);
        }
        if (!reserva.getStatus().equals("Em andamento")) {
            throw new CheckOutInvalidoException("Check-out não permitido. A reserva não está em andamento.");
        }       
        LocalDateTime agora = LocalDateTime.now();       
        if (agora.isAfter(reserva.getDataCheckOut().withHour(12).withMinute(0).withSecond(0))) {
            throw new CheckOutInvalidoException("Check-out não permitido após as 12h do dia de saída.");
        }        
        reserva.setStatus("Finalizada");
        reserva.getHospedagem().liberar();      
    }
    
    public List<Reserva> gerarRelatorioReservasAtivas() {
        List<Reserva> reservasAtivas = new ArrayList<>();
        for (Reserva reserva : hotelRepository.getReservas()) {
        	if (reserva.getStatus().equals("Em andamento"))  {
                reservasAtivas.add(reserva);
            }
        }
        reservasAtivas.sort((r1, r2) -> r1.getDataCheckIn().compareTo(r2.getDataCheckIn()));
        return reservasAtivas;
    }
    
    public double calcularTaxaOcupacao() {
        int totalHospedagens = hotelRepository.getHospedagens().size();
        int reservasAtivas = 0;
        for (Reserva reserva : hotelRepository.getReservas()) {
            if (reserva.getStatus().equals("Em andamento")) {
                reservasAtivas++;
            }
        }
        if (totalHospedagens == 0) {
            return 0.0; 
        }
        return (double) reservasAtivas / totalHospedagens * 100;
    }
    
    public List<Reserva> gerarRelatorioCancelamentos() {
        List<Reserva> cancelamentos = new ArrayList<>();
        for (Reserva reserva : hotelRepository.getReservas()) {
            if (reserva.getStatus().equals("cancelada")) {
                cancelamentos.add(reserva);
            }
        }
        return cancelamentos;
    }
    
    public double calcularFaturamentoTotal() {
        double faturamentoTotal = 0.0;
        for (Reserva reserva : hotelRepository.getReservas()) {
            if (reserva.getStatus().equals("finalizada")) {
                faturamentoTotal += reserva.getValorTotal();
            }
        }
        return faturamentoTotal;
    }
    
    public List<Reserva> gerarHistoricoCliente(String cpfCliente) {
        List<Reserva> historico = new ArrayList<>();
        for (Reserva reserva : hotelRepository.getReservas()) {
            if (reserva.getCliente().getCpf().equals(cpfCliente)) {
                historico.add(reserva);
            }
        }
        return historico;
    }
   }
