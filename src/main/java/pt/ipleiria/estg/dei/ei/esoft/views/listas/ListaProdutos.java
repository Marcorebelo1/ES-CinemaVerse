package pt.ipleiria.estg.dei.ei.esoft.views.listas;

import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListaProdutos {
    private List<Produto> produtos = new ArrayList<>();

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public List<String> getCategorias() {
        // Devolve lista de categorias de todos os produtos
        return produtos.stream().map(Produto::getCategoria).distinct().toList();
    }

    public void adicionarProduto(Produto produto) {
        if (produtos == null) {
            produtos = new ArrayList<>();
        }
        produtos.add(produto);
    }
}
