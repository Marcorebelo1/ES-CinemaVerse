package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.time.LocalDate;

public class Sessao {
    private Filme filme;
    private LocalDate data;
    private Horario horario;
    private Sala sala;

    public Sessao(Filme filme, LocalDate data, Horario horario, Sala sala) {
        this.filme = filme;
        this.data = data;
        this.horario = horario;
        this.sala = sala;
    }

    public Filme getFilme() {
        return filme;
    }

    public LocalDate getData() {
        return data;
    }

    public Horario getHorario() {
        return horario;
    }

    public Sala getSala() {
        return sala;
    }

    public boolean conflitaCom(Sessao outra) {
        if (!this.sala.equals(outra.sala)) return false;
        if (!this.data.equals(outra.data)) return false;
        return this.horario.conflitaCom(outra.horario);
    }

    @Override
    public String toString() {
        return data + " " + horario.getInicio() + " - " + filme.getTitulo() + " @ " + sala.getId();
    }
}
