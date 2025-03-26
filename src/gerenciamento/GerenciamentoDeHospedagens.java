package gerenciamento;

import entidades.Cliente;
import entidades.Hospedagem;
import repositorio.RepositorioDoHotel;

import java.util.ArrayList;
import java.util.List;

public class GerenciamentoDeHospedagens {
	 private RepositorioDoHotel hotelRepository;

	    public GerenciamentoDeHospedagens(RepositorioDoHotel hotelRepository) {
	        this.hotelRepository = hotelRepository;
	    }

	    public boolean cadastrarHospedagem(Hospedagem novahospedagem) {	       
	        for(Hospedagem hospedagem : hotelRepository.getHospedagens()) {
	            if (hospedagem.getId().equals(novahospedagem.getId())) {
	                return false;
	            }
	        }
	        hotelRepository.addHospedagem(novahospedagem);
	        return true;
	    }
	    
	    public Hospedagem buscarHospedagemPorId(String id) {
	        for (Hospedagem hospedagem : hotelRepository.getHospedagens()) {
	            if (hospedagem.getId().equals(id)) {
	                return hospedagem;
	            }
	        }
	        return null;
	    }
	    
	    public List<Hospedagem> listarHospedagensDisponiveis() {
	        List<Hospedagem> disponiveis = new ArrayList<>();
	        for (Hospedagem hospedagem : hotelRepository.getHospedagens()) {
	            if (hospedagem.isDisponivel()) {
	                disponiveis.add(hospedagem);
	            }
	        }
	        return disponiveis;
	    }
   
	    public List<Hospedagem> listarHospedagens() {
	        return hotelRepository.getHospedagens(); 
	    }
}
