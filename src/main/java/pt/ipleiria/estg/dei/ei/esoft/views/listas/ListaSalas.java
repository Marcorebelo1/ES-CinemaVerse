package pt.ipleiria.estg.dei.ei.esoft.views.listas;

import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a lista de salas (modelo de dados real).
 * Substitui o antigo repositório.
 */
public class ListaSalas {

    private final List<Sala> salas = new ArrayList<>();

    /**
     * Adiciona uma nova sala à lista.
     * @param sala Sala a adicionar
     * @return true se adicionou com sucesso, false em caso de erro
     */
    public boolean addToEndOfList(Sala sala) {
        try {
            salas.add(sala);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*Devolve uma cópia da lista de salas*/
    public List<Sala> getSalas() {
        return new ArrayList<>(salas);
    }


}