package entidades;

public class Apartamento extends Hospedagem {
	  public Apartamento(String id, int capacidadeMaxima, double precoDiaria) {
	        super(id, capacidadeMaxima, precoDiaria);
	    }

	    @Override
	    public double calcularValorHospedagem(int dias) {
	        if (dias > 7) {
	            return getPrecoDiaria() * dias * 0.9; 
	        }
	        return getPrecoDiaria() * dias;
	    }

}
