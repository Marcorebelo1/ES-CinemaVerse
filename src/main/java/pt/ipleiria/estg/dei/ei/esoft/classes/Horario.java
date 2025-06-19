package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.time.LocalTime;

public class Horario {
    private LocalTime inicio;
    private LocalTime fim;

    public Horario(LocalTime inicio, LocalTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public boolean conflitaCom(Horario outro) {
        return !(this.fim.isBefore(outro.inicio) || outro.fim.isBefore(this.inicio));
    }

    @Override
    public String toString() {
        return inicio + " - " + fim;
    }
}
