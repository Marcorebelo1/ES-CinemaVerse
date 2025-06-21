package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupAdicionarSala;

import static org.junit.jupiter.api.Assertions.*;
// Author: Marco Rebelo
public class SalaTestCase {


    //Verifica se é adicionada corretamente, se é quantiadade da lista aumentou e se a nova Sala foi adicionada no Fim
    @Test
    public void testAdicionarSala() {
        Sala novaSala = new Sala("Sala A", 5, 10, true, false, true);
        int quantidadesalas = DadosApp.getInstance().getListaSalas().getSalas().size();

        boolean response = DadosApp.getInstance().getListaSalas().addToEndOfList(novaSala);

        assertTrue(response);
        assertEquals(quantidadesalas+1, DadosApp.getInstance().getListaSalas().getSalas().size(), "Sala Adicionada com sucesso!");
        assertEquals(novaSala, DadosApp.getInstance().getListaSalas().getSalas().getLast());
    }

    //Verifica a mudança correta de estado da sala
    @Test
    public void testSetEstado() {
        Sala sala = new Sala("Sala A", 5, 10, true, true, false);

        // Testar inativar
        sala.setAtiva(false);
        assertFalse(sala.isAtiva());

        // Testar reativar
        sala.setAtiva(true);
        assertTrue(sala.isAtiva());
    }

    //Simula a atualização de campos
    @Test
    public void testAtualizarSala() {
        Sala sala = new Sala("Terror", 5, 10, true, false, true);

        // Simula edição
        sala.setNome("Atualizada");
        sala.setNumFilas(8);
        sala.setLugaresPorFila(12);
        sala.setDolbyAtmos(false);
        sala.setAcessibilidade(true);
        sala.setArCondicionado(false);

        // Verificações
        assertEquals("Atualizada", sala.getNome());
        assertEquals(8, sala.getNumFilas());
        assertEquals(12, sala.getLugaresPorFila());
        assertFalse(sala.isDolbyAtmos());
        assertTrue(sala.isAcessibilidade());
        assertFalse(sala.isArCondicionado());
    }

    //Teste garante que as salas inativas nao surgem nas sessoes
    @Test
    public void testSalasAtivas() {
        Sala ativa = new Sala("Ativa", 5, 10, false, false, false);
        Sala inativa = new Sala("Inativa", 6, 12, true, true, true);
        inativa.setAtiva(false);

        var lista = DadosApp.getInstance().getListaSalas();
        lista.addToEndOfList(ativa);
        lista.addToEndOfList(inativa);

        var ativas = lista.getSalas().stream().filter(Sala::isAtiva).toList();

        assertTrue(ativas.contains(ativa));
        assertFalse(ativas.contains(inativa));
    }
}