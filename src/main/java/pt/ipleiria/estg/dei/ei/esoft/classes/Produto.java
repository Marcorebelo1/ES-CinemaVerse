package pt.ipleiria.estg.dei.ei.esoft.classes;

public class Produto {
    private String nome;
    private String categoria;
    private double preco;

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public Produto(String nome, String categoria, double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }
}
