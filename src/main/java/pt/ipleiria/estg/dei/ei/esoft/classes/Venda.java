package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;
import java.time.LocalDate;

public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    private Produto produto;
    private LocalDate data;

    public Venda(Produto produto, LocalDate data) {
        this.produto = produto;
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public LocalDate getData() {
        return data;
    }
}
