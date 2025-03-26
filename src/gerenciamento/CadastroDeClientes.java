package gerenciamento;

import entidades.Cliente;
import repositorio.RepositorioDoHotel;
import java.util.List;

public class CadastroDeClientes {
    private RepositorioDoHotel hotelRepository;

    public CadastroDeClientes(RepositorioDoHotel hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean cadastrarCliente(String nome, String cpf, String telefone) {
        for (Cliente cliente : hotelRepository.getClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                return false;
            }
        }
        Cliente cliente = new Cliente(nome, cpf, telefone);
        hotelRepository.addCliente(cliente);
        return true;
    }
    
    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : hotelRepository.getClientes()) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Cliente> listarClientes() {
        return hotelRepository.getClientes();
    }
}