package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelSalas;

import javax.swing.*;
import java.awt.*;

/*
Popup Adicionar Sala
*/
public class PopupAdicionarSala extends JDialog {

    public PopupAdicionarSala(JFrame parentFrame, PainelSalas painelSalas) {
        super(parentFrame, "Adicionar Sala", true);
        setLayout(new BorderLayout(10, 10));

        // Painel principal com os campos do formulário
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        JLabel lblFilas = new JLabel("Número De Filas");
        JLabel lblLugares = new JLabel("Número De Lugares Por Fila");
        JLabel lblDolby = new JLabel("Dolbly Atmos");
        JLabel lblAcess = new JLabel("Acessibilidade");
        JLabel lblAC = new JLabel("AC");

        JTextField txtFilas = new JTextField();
        JTextField txtLugares = new JTextField();

        String[] opcoesSimNao = {"Y", "N"};
        JComboBox<String> cbDolby = new JComboBox<>(opcoesSimNao);
        JComboBox<String> cbAcess = new JComboBox<>(opcoesSimNao);
        JComboBox<String> cbAC = new JComboBox<>(opcoesSimNao);

        formPanel.add(lblFilas); formPanel.add(txtFilas);
        formPanel.add(lblLugares); formPanel.add(txtLugares);
        formPanel.add(lblDolby); formPanel.add(cbDolby);
        formPanel.add(lblAcess); formPanel.add(cbAcess);
        formPanel.add(lblAC); formPanel.add(cbAC);

        // Painel do botão confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirmar);

        // Botão fechar com "X"
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Listener do botão confirmar
        btnConfirmar.addActionListener(e -> {
            try {
                int filas = Integer.parseInt(txtFilas.getText());
                int lugares = Integer.parseInt(txtLugares.getText());

                if (filas <= 0 || lugares <= 0) {
                    JOptionPane.showMessageDialog(this, "Número de filas e lugares deve ser > 0", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean dolby = cbDolby.getSelectedItem().equals("Y");
                boolean acess = cbAcess.getSelectedItem().equals("Y");
                boolean ac = cbAC.getSelectedItem().equals("Y");

                Sala novaSala = new Sala(filas, lugares, dolby, acess, ac);
                //Vai à lista de salas adicionar ao final da lista
                boolean sucesso = DadosApp.getInstance().getListaSalas().addToEndOfList(novaSala);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Sala criada com sucesso!");
                    painelSalas.atualizarLista(); // Atualiza a lista na interface após adicionar
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao guardar a sala.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos inválidos. Use apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Junta os painéis ao popup
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 350);
        setLocationRelativeTo(parentFrame);
    }
}
