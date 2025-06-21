package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;

import javax.swing.*;
import java.awt.*;

/**
 * Popup que apresenta os detalhes da sala selecionada e permite modificar ou inativar/ativar.
 */
public class PopupDetalhesSala extends JDialog {

    // Campos editáveis
    private JTextField txtNome,txtFilas, txtLugares;
    private JComboBox<String> cbDolby, cbAcess, cbAC, cbEstado;
    private JLabel lblEstado;

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

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Nome da Sala
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Nome"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(sala.getNome());
        formPanel.add(txtNome, gbc);
        y++;

        // Campo do ID (não editável)
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("ID"), gbc);
        gbc.gridx = 1;
        JTextField txtId = new JTextField(String.valueOf(sala.getId()));
        txtId.setEditable(false);
        formPanel.add(txtId, gbc);
        y++;

        // Número de Filas
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Número De Filas"), gbc);
        gbc.gridx = 1;
        txtFilas = new JTextField(String.valueOf(sala.getNumFilas()));
        formPanel.add(txtFilas, gbc);
        y++;

        // Lugares por Fila
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Número De Lugares Por Fila"), gbc);
        gbc.gridx = 1;
        txtLugares = new JTextField(String.valueOf(sala.getLugaresPorFila()));
        formPanel.add(txtLugares, gbc);
        y++;

        // Campos booleanos como dropdowns
        String[] opcoes = {"Y", "N"};
        String[] opcoes2 = {"A", "I"};

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Dolby Atmos"), gbc);
        gbc.gridx = 1;
        cbDolby = new JComboBox<>(opcoes);
        cbDolby.setSelectedItem(sala.isDolbyAtmos() ? "Y" : "N");
        formPanel.add(cbDolby, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Acessibilidade"), gbc);
        gbc.gridx = 1;
        cbAcess = new JComboBox<>(opcoes);
        cbAcess.setSelectedItem(sala.isAcessibilidade() ? "Y" : "N");
        formPanel.add(cbAcess, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("AC"), gbc);
        gbc.gridx = 1;
        cbAC = new JComboBox<>(opcoes);
        cbAC.setSelectedItem(sala.isArCondicionado() ? "Y" : "N");
        formPanel.add(cbAC, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Estado"), gbc);
        gbc.gridx = 1;
        lblEstado = new JLabel(sala.isAtiva() ? "Ativo" : "Inativo");
        formPanel.add(lblEstado, gbc);

        // Botões de ação
        JButton btnModificar = new JButton("Modificar Sala Selecionada");
        JButton btnInativarAtivar = new JButton(sala.isAtiva() ? "Inativar" : "Ativar");

        btnModificar.addActionListener(e -> {
            try {
                boolean temSessao = DadosApp.getInstance().getListaSessoes().getSessoes().stream().anyMatch(s -> s.getSala() == sala);
                if (temSessao) {
                    JOptionPane.showMessageDialog(this, "Não é possível modificar uma sala com sessões agendadas.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String nomeNovo = txtNome.getText().trim();
                int novasFilas = Integer.parseInt(txtFilas.getText());
                int novosLugares = Integer.parseInt(txtLugares.getText());

                if (nomeNovo.isEmpty() || novasFilas <= 0 || novosLugares <= 0) {
                    JOptionPane.showMessageDialog(this, "Número de filas e lugares deve ser > 0", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean dolby = cbDolby.getSelectedItem().equals("Y");
                boolean acess = cbAcess.getSelectedItem().equals("Y");
                boolean ac = cbAC.getSelectedItem().equals("Y");
                boolean ativa = sala.isAtiva();

                sala.setNome(nomeNovo);
                sala.setNumFilas(novasFilas);
                sala.setLugaresPorFila(novosLugares);
                sala.setDolbyAtmos(dolby);
                sala.setAcessibilidade(acess);
                sala.setArCondicionado(ac);
                sala.setAtiva(ativa);

                JOptionPane.showMessageDialog(this, "Sala modificada com sucesso!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnInativarAtivar.addActionListener(e -> {
            boolean novoEstado = !sala.isAtiva();
            sala.setAtiva(novoEstado);
            lblEstado.setText(novoEstado ? "Ativo" : "Inativo");
            btnInativarAtivar.setText(novoEstado ? "Inativar" : "Ativar");
            JOptionPane.showMessageDialog(this,
                    novoEstado ? "Sala reativada com sucesso!" : "Sala inativada com sucesso!",
                    "Estado da Sala", JOptionPane.INFORMATION_MESSAGE);
        });

        gbc.gridx = 0; gbc.gridy = ++y;
        gbc.gridwidth = 2;
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.add(btnModificar);
        botoesPanel.add(btnInativarAtivar);
        formPanel.add(botoesPanel, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
}