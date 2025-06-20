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
        JLabel lblNome = new JLabel("Nome da Sala:");
        JLabel lblFilas = new JLabel("Número de Filas:");
        JLabel lblLugares = new JLabel("Lugares por Fila:");
        JLabel lblDolby = new JLabel("Dolby Atmos:");
        JLabel lblAcess = new JLabel("Acessibilidade:");
        JLabel lblAC = new JLabel("Ar Condicionado:");

        JTextField txtNome = new JTextField();
        JTextField txtFilas = new JTextField();
        JTextField txtLugares = new JTextField();

        String[] opcoesSimNao = {"Y", "N"};
        JComboBox<String> cbDolby = new JComboBox<>(opcoesSimNao);
        JComboBox<String> cbAcess = new JComboBox<>(opcoesSimNao);
        JComboBox<String> cbAC = new JComboBox<>(opcoesSimNao);

        formPanel.add(lblNome); formPanel.add(txtNome);
        formPanel.add(lblFilas); formPanel.add(txtFilas);
        formPanel.add(lblLugares); formPanel.add(txtLugares);
        formPanel.add(lblDolby); formPanel.add(cbDolby);
        formPanel.add(lblAcess); formPanel.add(cbAcess);
        formPanel.add(lblAC); formPanel.add(cbAC);

        // Botão Confirmar
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> btnConfirmarActionPerformed(
                txtNome,
                txtFilas,
                txtLugares,
                cbDolby,
                cbAcess,
                cbAC,
                painelSalas
        ));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirmar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 350);
        setLocationRelativeTo(parentFrame);
    }

    private void btnConfirmarActionPerformed(
            JTextField txtNome,
            JTextField txtFilas,
            JTextField txtLugares,
            JComboBox<String> cbDolby,
            JComboBox<String> cbAcess,
            JComboBox<String> cbAC,
            PainelSalas painelSalas
    ) {
        try {
            String nome = txtNome.getText().trim();
            int filas = Integer.parseInt(txtFilas.getText());
            int lugares = Integer.parseInt(txtLugares.getText());

            if (nome.isEmpty() || filas <= 0 || lugares <= 0) {
            mostrarErro("Todos os campos devem ser preenchidos corretamente.");
            return;
            }

            boolean dolby = cbDolby.getSelectedItem().equals("Y");
            boolean acess = cbAcess.getSelectedItem().equals("Y");
            boolean ac = cbAC.getSelectedItem().equals("Y");

            Sala novaSala = new Sala(nome, filas, lugares, dolby, acess, ac);
            boolean response = DadosApp.getInstance().getListaSalas().addToEndOfList(novaSala);
            if (response) {
                JOptionPane.showMessageDialog(this, "Sala criada com sucesso!");
                painelSalas.atualizarLista();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao guardar a sala.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            //validação
            mostrarErro("Campos numéricos inválidos. (Nº de Filas e Lugares sao campos numericos, nao utilizam texto)");
        }
    }

    private void mostrarErro(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
