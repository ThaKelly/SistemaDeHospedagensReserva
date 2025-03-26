package entidades;

public class Quarto extends Hospedagem {
	
	 private static final int capacidadeMaxima = 4;
	 
	public Quarto (String id, double precoDiaria) {
        super(id, capacidadeMaxima, precoDiaria);
    }
	
	    @Override
	    public double calcularValorHospedagem(int dias) {
	        return getPrecoDiaria() * dias; 
	    }	

}
