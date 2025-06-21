package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
// Author: Marco Rebelo
public class FilmeTestCase {

    //Verifica o Alugar Filme
    @Test
    public void testAlugarFilmeDoCatalogo() {
        String titulo = "Matrix";
        Filme catalogo = new Filme(titulo, 120, "M/16", "Ação", "Original", true, "Netflix", 7, LocalDate.now().minusDays(30));
        DadosApp.getInstance().getFilmesCatalogo().add(catalogo);

        Filme alugado = new Filme(
                catalogo.getTitulo(),
                catalogo.getDuracao(),
                catalogo.getClassificacaoEtaria(),
                catalogo.getCategoria(),
                catalogo.getVersao(),
                catalogo.isIs3D(),
                catalogo.getFornecedor(),
                7,
                LocalDate.now()
        );

        boolean result = DadosApp.getInstance().getListaFilmes().addToEndOfList(alugado);

        assertTrue(result);
        assertEquals(alugado, DadosApp.getInstance().getListaFilmes().getFilmes().getLast());
    }

    //Verifica se as licencas estão ativas ou nao
    @Test
    public void testLicencasFilmes() {

        // Filme com licença ainda válida (7 dias de licença, alugado há 5 dias)
        Filme filmeAtivo = new Filme("Ativo", 100, "M/12", "Aventura", "Original", false, "Fornecedor X", 7, LocalDate.now().minusDays(5));

        // Filme com licença expirada (7 dias de licença, alugado há 10 dias)
        Filme filmeExpirado = new Filme("Expirado", 90, "M/16", "Terror", "Dublada", true, "Fornecedor Y", 7, LocalDate.now().minusDays(10));

        // Verificações
        assertTrue(filmeAtivo.isAtivo(), "O filme 'Ativo' deveria estar dentro do período de licença.");
        assertFalse(filmeExpirado.isAtivo(), "O filme 'Expirado' já deveria ter a licença expirada.");

    }


}
