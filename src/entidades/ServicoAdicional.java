package entidades;

public class ServicoAdicional{
	private String id; 
    private String descricao; 
    private double preco; 
    private boolean disponivel;

    public ServicoAdicional(String id, String descricao, double preco) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }
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

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }
}
