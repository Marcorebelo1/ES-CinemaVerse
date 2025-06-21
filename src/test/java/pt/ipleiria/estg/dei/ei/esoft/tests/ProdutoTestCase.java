package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;

import static org.junit.jupiter.api.Assertions.*;

// Author: Bruno Sampaio
public class ProdutoTestCase {

    @Test
    public void testAdicionarProduto() {
        String nomeProduto = "Coca-Cola";
        int quantidadeProduto = DadosApp.getInstance().getListaProdutos().getProdutos().size();
        var produto = new
                Produto(nomeProduto, "Bebidas", 1.50, 100);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);
        assertEquals(quantidadeProduto + 1, DadosApp.getInstance().getListaProdutos().getProdutos().size());

    }

    @Test
    public void testSetEstado() {
        Produto produto = new Produto("Fanta", "Bebidas", 1.10, 30);
        produto.setEstado(false);
        assertFalse(produto.isAtivo());
        produto.setEstado(true);
        assertTrue(produto.isAtivo());
    }

    @Test
    public void testEditarProduto() {
        Produto produto = new Produto("Pepsi", "Bebidas", 1.20, 50);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto);

        produto.setNome("Pepsi Max");
        produto.setCategoria("Bebidas Dietéticas");
        produto.setPreco(1.30);
        produto.setStock(40);

        assertEquals("Pepsi Max", produto.getNome());
        assertEquals("Bebidas Dietéticas", produto.getCategoria());
        assertEquals(1.30, produto.getPreco());
        assertEquals(40, produto.getStock());
    }


}
