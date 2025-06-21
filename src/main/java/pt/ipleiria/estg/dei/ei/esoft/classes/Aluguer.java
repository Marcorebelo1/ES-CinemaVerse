package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;
import java.time.LocalDate;

public class Aluguer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Filme filme;
    private LocalDate data;
    private double preco; // Preço do aluguer

    public Aluguer(Filme filme, LocalDate data, double preco) {
        this.filme = filme;
        this.data = data;
        this.preco = preco;
    }

    public Filme getFilme() {
        return filme;
    }

    public LocalDate getData() {
        return data;
    }

    public double getPreco() {
        return preco;
    }
}
