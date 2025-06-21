package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.classes.Venda;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class EstatisticaTestCase {


    @Test
    public void testEstatisticasVendas() {
        // Limpa e adiciona produtos e vendas para garantir o total de 100.0
        DadosApp.getInstance().getListaProdutos().getProdutos().clear();
        DadosApp.getInstance().getVendas().clear();

        double totalVendasAntigo = DadosApp.getInstance().getVendas().stream()
                .mapToDouble(venda -> venda.getProduto().getPreco())
                .sum();

        // Adiciona produtos
        var produto1 = new Produto("Produto1", "Categoria1", 25.0, 10);
        var produto2 = new Produto("Produto2", "Categoria2", 35.0, 10);
        var produto3 = new Produto("Produto3", "Categoria3", 40.0, 10);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto1);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto2);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(produto3);

        // Adiciona vendas
        DadosApp.getInstance().adicionarVenda(new Venda(produto1, LocalDate.now()));
        DadosApp.getInstance().adicionarVenda(new Venda(produto2, LocalDate.now()));
        DadosApp.getInstance().adicionarVenda(new Venda(produto3, LocalDate.now()));

        double totalVendas = DadosApp.getInstance().getVendas().stream()
                .mapToDouble(venda -> venda.getProduto().getPreco())
                .sum();
        assertEquals(totalVendasAntigo + 100.0, totalVendas, 0.01);
    }

    @Test
    public void testEstatisticaBilhetesVendidos() {
        DadosApp.getInstance().getListaProdutos().getProdutos().clear();
        DadosApp.getInstance().getVendas().clear();

        Produto bilhete = new Produto("Bilhete Normal", "Bilhete", 7.5, 100);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(bilhete);
        DadosApp.getInstance().adicionarVenda(new Venda(bilhete, LocalDate.now()));

        long count = DadosApp.getInstance().getVendas().stream()
                .filter(v -> v.getProduto().getCategoria().equals("Bilhete"))
                .count();
        assertEquals(1, count);
    }

    @Test
    public void testEstatisticaRefrigerantesVendidos() {
        DadosApp.getInstance().getListaProdutos().getProdutos().clear();
        DadosApp.getInstance().getVendas().clear();

        Produto refri = new Produto("Coca-Cola", "Bebida", 2.0, 50);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(refri);
        DadosApp.getInstance().adicionarVenda(new Venda(refri, LocalDate.now()));

        double total = DadosApp.getInstance().getVendas().stream()
                .filter(v -> v.getProduto().getCategoria().equals("Bebida"))
                .mapToDouble(v -> v.getProduto().getPreco())
                .sum();
        assertEquals(2.0, total, 0.01);
    }

    @Test
    public void testEstatisticaPipocasVendidas() {
        DadosApp.getInstance().getListaProdutos().getProdutos().clear();
        DadosApp.getInstance().getVendas().clear();

        long countAntigo = DadosApp.getInstance().getVendas().stream()
                .filter(v -> v.getProduto().getNome().toLowerCase().contains("pipocas"))
                .count();

        Produto pipoca = new Produto("Pipocas Doces", "Comida", 3.5, 30);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(pipoca);
        DadosApp.getInstance().adicionarVenda(new Venda(pipoca, LocalDate.now()));

        long count = DadosApp.getInstance().getVendas().stream()
                .filter(v -> v.getProduto().getNome().toLowerCase().contains("pipocas"))
                .count();
        assertEquals(countAntigo + 1, count);
    }

    @Test
    public void testEstatisticaMenusVendidos() {
        DadosApp.getInstance().getListaProdutos().getProdutos().clear();
        DadosApp.getInstance().getVendas().clear();

        Produto menu = new Produto("Menu Pipocas", "Comida", 5.0, 20);
        DadosApp.getInstance().getListaProdutos().adicionarProduto(menu);
        DadosApp.getInstance().adicionarVenda(new Venda(menu, LocalDate.now()));

        long count = DadosApp.getInstance().getVendas().stream()
                .filter(v -> v.getProduto().getNome().toLowerCase().contains("menu"))
                .count();
        assertEquals(1, count);
    }
}

