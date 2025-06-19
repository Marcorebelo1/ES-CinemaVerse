package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.classes.Sessao;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;

import javax.swing.*;
import java.awt.*;

/**
 * Pop-up que mostra o mapa da sala com os lugares disponíveis e ocupados.
 */
public class PopupLugaresDisponiveis extends JDialog {

    public PopupLugaresDisponiveis(JFrame parent, Sessao sessao) {
        super(parent, "Mapa de Lugares", true);
        setLayout(new BorderLayout(10, 10));

        Sala sala = sessao.getSala();
        int filas = sala.getNumFilas();
        int lugaresPorFila = sala.getLugaresPorFila();

        JPanel grid = new JPanel(new GridLayout(filas, lugaresPorFila, 5, 5));
        for (int f = 0; f < filas; f++) {
            for (int l = 0; l < lugaresPorFila; l++) {
                JButton btnLugar = new JButton((f + 1) + "-" + (l + 1));
                btnLugar.setBackground(Color.GREEN); // Todos disponíveis por agora
                grid.add(btnLugar);
            }
        }

        add(new JLabel("Sala: " + sala.getNome(), SwingConstants.CENTER), BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);

        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
}
