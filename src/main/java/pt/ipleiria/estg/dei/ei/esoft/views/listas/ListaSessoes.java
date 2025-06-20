package pt.ipleiria.estg.dei.ei.esoft.views.listas;

import pt.ipleiria.estg.dei.ei.esoft.classes.Horario;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sessao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a lista de sessões (modelo de dados real).
 */
public class ListaSessoes implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Sessao> sessoes = new ArrayList<>();

    /**
     * Adiciona uma sessão ao final da lista, se não houver conflitos de horário na mesma sala.
     */
    public boolean addToEndOfList(Sessao nova) {
        boolean conflito = sessoes.stream()
                .anyMatch(sessao -> sessao.conflitaCom(nova));
        if (conflito) {
            return false; // Já existe sessão com conflito
        }
        return sessoes.add(nova);
    }

    /**
     * Verifica se a sala está disponível num dado dia e horário.
     */
    public boolean isSalaDisponivel(Sala sala, LocalDate data, Horario horario) {
        return sessoes.stream()
                .filter(s -> s.getSala().equals(sala))
                .filter(s -> s.getData().equals(data))
                .noneMatch(s -> s.getHorario().conflitaCom(horario));
    }

    /**
     * Devolve uma cópia da lista de sessões.
     */
    public List<Sessao> getSessoes() {
        return new ArrayList<>(sessoes);
    }

    /**
     * Remove uma sessão se não for passada e não tiver bilhetes (a lógica dos bilhetes seria externa).
     */
    public boolean removerSessao(Sessao sessao) {
        return sessoes.remove(sessao);
    }
}