package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;
import java.time.LocalTime;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public String getBlocoHorario() {
        int hora = inicio.getHour();
        int horaInicio = (hora / 3) * 3;
        int horaFim = horaInicio + 3;
        String inicio = String.format("%02d:00", horaInicio);
        String fim = String.format("%02d:00", horaFim % 24); // wrap around at 24
        return inicio + " – " + fim;
    }
}
