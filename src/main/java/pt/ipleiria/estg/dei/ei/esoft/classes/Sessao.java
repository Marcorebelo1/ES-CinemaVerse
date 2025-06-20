package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sessao implements Serializable {
    private static final long serialVersionUID = 1L;
    private Filme filme;
    private LocalDate data;
    private Horario horario;
    private Sala sala;
    private List<List<Boolean>> lugaresOcupados;

    public Sessao(Filme filme, LocalDate data, Horario horario, Sala sala) {
        this.filme = filme;
        this.data = data;
        this.horario = horario;
        this.sala = sala;
        this.lugaresOcupados = new ArrayList<>(sala.getNumFilas());
        for (int i = 0; i < sala.getNumFilas(); i++) {
            List<Boolean> fila = new ArrayList<>(sala.getLugaresPorFila());
            for (int j = 0; j < sala.getLugaresPorFila(); j++) {
                fila.add(false); // Inicialmente todos os lugares estão livres
            }
            lugaresOcupados.add(fila);
        }
    }

    public boolean getLugarOcupado(int fila, int lugar) {
        if (fila < 0 || fila >= sala.getNumFilas() || lugar < 0 || lugar >= sala.getLugaresPorFila()) {
            throw new IndexOutOfBoundsException("Fila ou lugar inválido.");
        }
        return lugaresOcupados.get(fila).get(lugar);
    }

    public void setLugarOcupado(int fila, int lugar, boolean ocupado) {
        if (fila < 0 || fila >= sala.getNumFilas() || lugar < 0 || lugar >= sala.getLugaresPorFila()) {
            throw new IndexOutOfBoundsException("Fila ou lugar inválido.");
        }
        lugaresOcupados.get(fila).set(lugar, ocupado);
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
