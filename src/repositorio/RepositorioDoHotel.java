package repositorio;
import java.util.ArrayList;
import java.util.List;

import entidades.Hospedagem;
import entidades.ServicoAdicional;
import entidades.Cliente;
import entidades.Funcionario;
import entidades.Reserva;

public class RepositorioDoHotel {
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Reserva> reservas;
    private List<Hospedagem> hospedagens;
    private List<ServicoAdicional> servicosAdicionais;
   
    private static final String CLIENTES_FILE = "clientes.dat";
    private static final String FUNCIONARIOS_FILE = "funcionarios.dat";
    private static final String RESERVAS_FILE = "reservas.dat";
    private static final String HOSPEDAGENS_FILE = "hospedagens.dat";
    private static final String SERVICOS_FILE = "servicos.dat";

    public RepositorioDoHotel() {
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.hospedagens = new ArrayList<>();
        this.servicosAdicionais = new ArrayList<>();                      
    }

    public void addCliente(Cliente cliente) {
        clientes.add(cliente);        
    }

    public void addReserva(Reserva reserva) {
        reservas.add(reserva);        
    }

    public void addHospedagem(Hospedagem hospedagem) {
        hospedagens.add(hospedagem);        
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);      
    }

    public void addServicoAdicional(ServicoAdicional servico) {
        servicosAdicionais.add(servico);  
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }

    public List<ServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }

   
    
}