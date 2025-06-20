package pt.ipleiria.estg.dei.ei.esoft.views.listas;

import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaProdutos implements Serializable {
    private List<Produto> produtos = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public List<String> getCategorias() {
        // Devolve lista de categorias de todos os produtos
        return produtos.stream().map(Produto::getCategoria).distinct().toList();
    }

    public boolean adicionarProduto(Produto produto) {
        try {
            if (produtos == null) {
                produtos = new ArrayList<>();

            }
            produtos.add(produto);
            return true;
        }catch (Exception e) {
            return false;
        }

    }
}
