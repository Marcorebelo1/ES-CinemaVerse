package pt.ipleiria.estg.dei.ei.esoft.views.listas;

import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a lista de filmes
 */
public class ListaFilmes {

    private final List<Filme> filmes = new ArrayList<>();

    /**
     * Adiciona um novo filme à lista.
     */
    public boolean addToEndOfList(Filme filme) {
        try {
            filmes.add(filme);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Devolve uma cópia da lista de filmes.
     */
    public List<Filme> getFilmes() {
        return new ArrayList<>(filmes);
    }

    public Filme getFilmeByTitulo(String titulo) {
        return filmes.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

}
