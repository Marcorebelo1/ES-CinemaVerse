package pt.ipleiria.estg.dei.ei.esoft.classes;

import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carrinho implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Produto> produtos = new ArrayList<>();
    private double totalPrice;
    private IListener listener;

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public Map<Produto, Integer> getProdutosQuantidade() {
        Map<Produto, Integer> produtosQuantidade = new HashMap<>();
        for (Produto produto : produtos) {
            produtosQuantidade.put(produto, produtosQuantidade.getOrDefault(produto, 0) + 1);
        }
        return produtosQuantidade;
    }

    public double getFinalPrice() {
        return totalPrice;
    }

    public void adicionarItem(Produto item) {
        produtos.add(item);
        totalPrice += item.getPreco();
        notifyListener();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < produtos.size()) {
            Produto produtoRemovido = produtos.remove(index);
            if (produtoRemovido instanceof Bilhete bilhete) {
                bilhete.getSessao().setLugarOcupado(bilhete.getNumeroFila(), bilhete.getNumeroLugar(), false);
            }
            totalPrice -= produtoRemovido.getPreco();
            notifyListener();
        }
    }

    public void clear() {
        produtos.clear();
        totalPrice = 0.0;
        notifyListener();
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    private void notifyListener() {
            if (listener != null) listener.update();
    }
}
