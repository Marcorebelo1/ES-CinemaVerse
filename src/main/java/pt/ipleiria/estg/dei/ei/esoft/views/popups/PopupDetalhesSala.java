package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;

import javax.swing.*;
import java.awt.*;

/**
 * Popup que apresenta os detalhes da sala selecionada e permite modificar ou inativar/ativar.
 */
public class PopupDetalhesSala extends JDialog {

    // Campos editáveis
    private JTextField txtFilas, txtLugares;
    private JComboBox<String> cbDolby, cbAcess, cbAC, cbEstado;

    /**
     * Construtor do popup de detalhes da sala.
     * @param parentFrame Janela pai (para centrar o popup)
     * @param sala Sala a ser visualizada
     */

    public PopupDetalhesSala(JFrame parentFrame, Sala sala) {

        super(parentFrame, "Características - Sala Selecionada", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 400);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);

        // Campo do ID (não editável)
        formPanel.add(new JLabel("ID"));
        JTextField txtId = new JTextField(String.valueOf(sala.getId()));
        txtId.setEditable(false);
        formPanel.add(txtId);

        // Campos editáveis
        formPanel.add(new JLabel("Número De Filas"));
        txtFilas = new JTextField(String.valueOf(sala.getNumFilas()));
        formPanel.add(txtFilas);

        formPanel.add(new JLabel("Número De Lugares Por Fila"));
        txtLugares = new JTextField(String.valueOf(sala.getLugaresPorFila()));
        formPanel.add(txtLugares);

        // Campos booleanos como dropdowns
        String[] opcoes = {"Y", "N"};
        String[] opcoes2 = {"A", "I"};

        formPanel.add(new JLabel("Dolby Atmos"));
        cbDolby = new JComboBox<>(opcoes);
        cbDolby.setSelectedItem(sala.isDolbyAtmos() ? "Y" : "N");
        formPanel.add(cbDolby);

        formPanel.add(new JLabel("Acessibilidade"));
        cbAcess = new JComboBox<>(opcoes);
        cbAcess.setSelectedItem(sala.isAcessibilidade() ? "Y" : "N");
        formPanel.add(cbAcess);

        formPanel.add(new JLabel("AC"));
        cbAC = new JComboBox<>(opcoes);
        cbAC.setSelectedItem(sala.isArCondicionado() ? "Y" : "N");
        formPanel.add(cbAC);

        formPanel.add(new JLabel("Estado"));
        cbEstado = new JComboBox<>(opcoes2);
        cbEstado.setSelectedItem(sala.isAtiva() ? "A" : "I");
        formPanel.add(cbEstado);


        //Botões de ação
        JButton btnModificar = new JButton("Modificar Sala Selecionada");
        JButton btnInativarAtivar = new JButton(sala.isAtiva() ? "Inativar" : "Ativar");

        // Ação para modificar a sala com os novos dados
        btnModificar.addActionListener(e -> {
            try {
                int novasFilas = Integer.parseInt(txtFilas.getText());
                int novosLugares = Integer.parseInt(txtLugares.getText());

                if (novasFilas <= 0 || novosLugares <= 0) {
                    JOptionPane.showMessageDialog(this, "Número de filas e lugares deve ser > 0", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean dolby = cbDolby.getSelectedItem().equals("Y");
                boolean acess = cbAcess.getSelectedItem().equals("Y");
                boolean ac = cbAC.getSelectedItem().equals("Y");
                boolean ativa = cbEstado.getSelectedItem().equals("Y");

                sala.setNumFilas(novasFilas);
                sala.setLugaresPorFila(novosLugares);
                sala.setDolbyAtmos(dolby);
                sala.setAcessibilidade(acess);
                sala.setArCondicionado(ac);
                sala.setAtiva(ativa);

                JOptionPane.showMessageDialog(this, "Sala modificada com sucesso!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação para alternar entre ativar e inativar a sala
        btnInativarAtivar.addActionListener(e -> {
            boolean novoEstado = !sala.isAtiva();
            sala.setAtiva(novoEstado);
            cbEstado.setSelectedItem(novoEstado ? "A" : "I");
            btnInativarAtivar.setText(novoEstado ? "Inativar" : "Ativar");
            JOptionPane.showMessageDialog(this,
                    novoEstado ? "Sala reativada com sucesso!" : "Sala inativada com sucesso!",
                    "Estado da Sala", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(btnModificar);
        botoesPanel.add(btnInativarAtivar);

        add(formPanel, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);
    }
}
