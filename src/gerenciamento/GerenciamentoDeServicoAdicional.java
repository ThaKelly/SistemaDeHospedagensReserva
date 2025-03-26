package gerenciamento;

import entidades.ServicoAdicional;
import repositorio.RepositorioDoHotel;

import java.util.ArrayList;
import java.util.List;

public class GerenciamentoDeServicoAdicional {
	private RepositorioDoHotel hotelRepository;

    public GerenciamentoDeServicoAdicional(RepositorioDoHotel hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public boolean cadastrarServicoAdicional(String id, String descricao, double preco) {
    	for(ServicoAdicional servico : hotelRepository.getServicosAdicionais()) {
            if (servico.getId().equals(id)) {
                return false;
            }
        }
        ServicoAdicional servico = new ServicoAdicional(id, descricao, preco);
        hotelRepository.addServicoAdicional(servico);
        return true;
    }
    
    public ServicoAdicional buscarServicoAdicionalPorId(String id) {
        for (ServicoAdicional servico : hotelRepository.getServicosAdicionais()) {
            if (servico.getId().equals(id)) {
                return servico;
            }
        }
        return null;
    }
    
    public List<ServicoAdicional> listarServicosDisponiveis() {
        List<ServicoAdicional> disponiveis = new ArrayList<>();
        for (ServicoAdicional servico : hotelRepository.getServicosAdicionais()) {
            if (servico.isDisponivel()) {
                disponiveis.add(servico);
            }
        }
        return disponiveis;
    }

    public List<ServicoAdicional> listarServicosAdicionais() {
        return hotelRepository.getServicosAdicionais(); 
    }
	

}
