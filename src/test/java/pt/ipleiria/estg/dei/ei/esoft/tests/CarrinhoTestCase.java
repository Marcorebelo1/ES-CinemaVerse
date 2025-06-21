package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.classes.Carrinho;

// Author: Miguel Pereira
public class CarrinhoTestCase {

    @Test
    public void testAdicionarCarrinho() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);
        DadosApp.getInstance().getCarrinho().adicionarItem(produto);
        assertEquals(produto, DadosApp.getInstance().getCarrinho().getProdutos().getLast());
    }

    @Test
    public void testRemoverCarrinho() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);
        DadosApp.getInstance().getCarrinho().adicionarItem(produto);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.removeItem(carrinho.getProdutos().size() - 1);

        assertFalse(carrinho.getProdutos().contains(produto));
    }

    @Test
    public void testComprarCarrinho() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);
        DadosApp.getInstance().getCarrinho().adicionarItem(produto);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.comprar();

        assertTrue(carrinho.getProdutos().isEmpty());
        assertEquals(0, carrinho.getFinalPrice());
    }

    @Test
    public void testFinalPriceAfterAddingItems() {
        String nomeProduto1 = "Bilhete Simples";
        var produto1 = new Produto(nomeProduto1, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto1);

        String nomeProduto2 = "Bilhete Premium";
        var produto2 = new Produto(nomeProduto2, "Bilhete", 20.0, 5);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto2);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        double initialPrice = carrinho.getFinalPrice();
        carrinho.adicionarItem(produto1);
        carrinho.adicionarItem(produto2);


        assertEquals(initialPrice + produto1.getPreco() + produto2.getPreco(), carrinho.getFinalPrice());
    }

    @Test
    public void testClearCarrinho() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);
        DadosApp.getInstance().getCarrinho().adicionarItem(produto);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.clear();

        assertTrue(carrinho.getProdutos().isEmpty());
        assertEquals(0, carrinho.getFinalPrice());
    }

    @Test
    public void testAdicionarItemComQuantidade() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto); // Adiciona o mesmo produto novamente

        assertEquals(2, carrinho.getProdutosQuantidade().get(produto).intValue());
    }

    @Test
    public void testRemoverItemComQuantidade() {
        String nomeProduto = "Bilhete Simples";
        var produto = new Produto(nomeProduto, "Bilhete", 10.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);

        Carrinho carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto); // Adiciona o mesmo produto novamente

        carrinho.removeItem(carrinho.getProdutos().size() - 1); // Remove uma instância do produto

        assertEquals(1, carrinho.getProdutosQuantidade().get(produto).intValue());
    }
}
