package entidades;

public class Cabana extends Hospedagem {
	 private double taxaTemporada; 

	    public Cabana(String id, int capacidadeMaxima, double precoDiaria, double taxaTemporada) {
	        super(id, capacidadeMaxima, precoDiaria);
	        this.taxaTemporada = taxaTemporada;
	    }

	    @Override
	    public double calcularValorHospedagem(int dias) {
	        return (getPrecoDiaria() + taxaTemporada) * dias;
	    }

}
