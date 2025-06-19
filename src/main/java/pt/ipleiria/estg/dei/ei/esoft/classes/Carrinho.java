package pt.ipleiria.estg.dei.ei.esoft.classes;

import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Produto> produtos = new ArrayList<>();
    private double totalPrice;
    private IListener listener;

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
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
            totalPrice -= produtoRemovido.getPreco();
            notifyListener();
        }
    }

    public void setListener(IListener listener) {
        this.listener = listener;
    }

    private void notifyListener() {
            if (listener != null) listener.update();
    }
}
