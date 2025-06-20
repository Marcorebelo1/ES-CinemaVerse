package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;

public class Bilhete extends Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Sessao sessao;
    private int numeroFila;
    private int numeroLugar;

    public Bilhete (Produto produto) {
        super(produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getStock());
    }

    public Bilhete(String nome, String categoria, double preco, int stock) {
        super(nome, categoria, preco, stock);
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public void setNumeroFila(int numeroFila) {
        this.numeroFila = numeroFila;
    }

    public int getNumeroLugar() {
        return numeroLugar;
    }

    public void setNumeroLugar(int numeroLugar) {
        this.numeroLugar = numeroLugar;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    @Override
    public String toString() {
        return nome + (sessao != null ? " - " + sessao.getFilme().getTitulo() : "");
    }
}
