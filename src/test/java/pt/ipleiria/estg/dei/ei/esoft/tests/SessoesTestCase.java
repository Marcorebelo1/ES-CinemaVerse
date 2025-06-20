package pt.ipleiria.estg.dei.ei.esoft.tests;

import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;
import pt.ipleiria.estg.dei.ei.esoft.classes.Horario;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sessao;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class SessoesTestCase {

    //Testa Sobreposicão entre Sessões
    @Test
    public void testConflitoDeSessoes() {
        Filme filme = new Filme("Aventura", 120, "M/12", "Ação", "Original", false, "Fox", 7, LocalDate.now());
        Sala sala = new Sala("Sala 1", 5, 10, false, false, false);

        // Sessão A das 14:00 às 16:00
        Sessao sessaoA = new Sessao(filme, LocalDate.of(2025, 6, 20), new Horario(LocalTime.of(14, 0), LocalTime.of(16, 0)), sala);

        // Sessão B sobreposta: das 15:00 às 17:00
        Sessao sessaoB = new Sessao(filme, LocalDate.of(2025, 6, 20), new Horario(LocalTime.of(15, 0), LocalTime.of(17, 0)), sala);

        // Sessão C sem conflito: das 16:30 às 18:00
        Sessao sessaoC = new Sessao(filme, LocalDate.of(2025, 6, 20), new Horario(LocalTime.of(16, 30), LocalTime.of(18, 0)), sala);

        assertTrue(sessaoA.conflitaCom(sessaoB), "Sessões A e B deviam estar em conflito.");
        assertFalse(sessaoA.conflitaCom(sessaoC), "Sessões A e C não deviam estar em conflito.");
    }

    //Testa Ocupação de Lugar na Sessão
    @Test
    public void testOcuparELiberarLugar() {
        Filme filme = new Filme("Interstellar", 150, "M/12", "Ficção", "Original", false, "Paramount", 14, LocalDate.now());
        Sala sala = new Sala("Sala Teste", 3, 4, false, false, false);
        Sessao sessao = new Sessao(filme, LocalDate.now(), new Horario(LocalTime.of(18, 0), LocalTime.of(20, 30)), sala);

        // Lugar deve estar livre inicialmente
        assertFalse(sessao.getLugarOcupado(1, 2));

        // Ocupar
        sessao.setLugarOcupado(1, 2, true);
        assertTrue(sessao.getLugarOcupado(1, 2));

        // Liberar
        sessao.setLugarOcupado(1, 2, false);
        assertFalse(sessao.getLugarOcupado(1, 2));
    }

}
