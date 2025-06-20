package pt.ipleiria.estg.dei.ei.esoft.classes;

public class Produto {
    private String nome;
    private String categoria;
    private double preco;
    private int stock;
    private boolean estado;


    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }
    public boolean isEstado() {
        return estado;
    }
    public double getPreco() {
        return preco;
    }

    public int getStock() {
        return stock;
    }

    public Produto(String nome, String categoria, double preco, int stock) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.stock = stock;
        estado = true;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return nome;
    }
}
