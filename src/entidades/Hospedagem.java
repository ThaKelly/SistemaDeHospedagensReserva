package entidades;

public abstract class Hospedagem  {
	private String id; 
    private int capacidadeMaxima;
    private double precoDiaria;
    private boolean disponivel; 
    
    public Hospedagem(String id, int capacidadeMaxima, double precoDiaria) {
        this.id = id;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoDiaria = precoDiaria;
        this.disponivel = true; 
    }
   
    public abstract double calcularValorHospedagem(int dias);

    public boolean isDisponivel() {
        return disponivel;
    }

    public void reservar() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }
 
    public String getId() {
        return id;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }
}
