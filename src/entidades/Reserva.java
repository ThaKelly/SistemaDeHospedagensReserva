package entidades;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private String id; 
    private Cliente cliente; 
    private Hospedagem hospedagem; 
    private List<ServicoAdicional> servicosAdicionais; 
    private LocalDateTime dataCheckIn; 
    private LocalDateTime dataCheckOut; 
    private String status;
    private String motivoCancelamento; 
  
    public Reserva(String id, Cliente cliente, Hospedagem hospedagem, LocalDateTime dataCheckIn, LocalDateTime dataCheckOut, String status) {
        this.id = id;
        this.cliente = cliente;
        this.hospedagem = hospedagem;
        this.servicosAdicionais = new ArrayList<>(); 
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.status = status;
    }
    
    public void adicionarServicoAdicional(ServicoAdicional servico) {
        this.servicosAdicionais.add(servico);
    }
    
    public boolean cancelarReserva(int horasAntes) {
        LocalDateTime agora = LocalDateTime.now();
        long diffHoras = ChronoUnit.HOURS.between(agora, dataCheckIn);
        
        if (diffHoras >= horasAntes) {
            this.status = "cancelada"; 
            return true; 
        }
        return false; 
    }
    
    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }
	
    public List<ServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }
    
    public double getValorTotal() {
        double valorHospedagem = hospedagem.getPrecoDiaria() * calcularNumeroDiarias(); 
        double valorServicos = 0.0;
        for (ServicoAdicional servico : servicosAdicionais) {
            valorServicos += servico.getPreco();
        }       
        return valorHospedagem + valorServicos;
    }
    
    private long calcularNumeroDiarias() {      
        return Duration.between(dataCheckIn, dataCheckOut).toDays();
    	
	}

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Hospedagem getHospedagem() {
        return hospedagem;
    }

    public LocalDateTime getDataCheckIn() {
        return dataCheckIn;
    }

    public LocalDateTime getDataCheckOut() {
        return dataCheckOut;
    }

    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}