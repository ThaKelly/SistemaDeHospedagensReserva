package gerenciamento;

import entidades.Funcionario;
import repositorio.RepositorioDoHotel;

import java.util.List;

public class CadastroDeFuncionarios {
    private RepositorioDoHotel hotelRepository;

    public CadastroDeFuncionarios(RepositorioDoHotel hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean cadastrarFuncionario(String id, String nome, String cargo) {
        for (Funcionario funcionario : hotelRepository.getFuncionarios()) {
            if (funcionario.getId().equals(id)) {
                return false;
            }
        }
        Funcionario funcionario = new Funcionario(id, nome, cargo);
        hotelRepository.addFuncionario(funcionario);
        return true;
    }

    public List<Funcionario> listarFuncionarios() {
        return hotelRepository.getFuncionarios();
    }

    public Funcionario buscarFuncionarioPorId(String id) {
        for (Funcionario funcionario : hotelRepository.getFuncionarios()) {
            if (funcionario.getId().equals(id)) {
                return funcionario;
            }
        }
        return null; 
    }
}

