package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;

public class Produto implements Serializable {
    protected static final long serialVersionUID = 1L;
    protected String nome;
    protected String categoria;
    protected double preco;
    protected int stock;
    protected boolean estado = true; // Default to true, indicating the product is active

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public int getStock() {
        return stock;
    }

    public boolean isAtivo() {
        return estado;
    }

    public void setEstado(boolean ativo) {
        this.estado = ativo;
    }

    public Produto(String nome, String categoria, double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.stock = 10; // Default stock to 10
    }

    public Produto(String nome, String categoria, double preco, int stock) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.stock = stock;
    }

    public void setStock(int newStock) {
        if (newStock >= 0) {
            this.stock = newStock;
        } else {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

    }

    @Override
    public String toString() {
        return nome;
    }
}
