package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Sessao;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupAdicionarSessao;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupEditarSessao;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupLugaresDisponiveis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Painel responsável por listar as sessões com filtros e opções de gestão.
 */
public class PainelSessoes extends JPanel {
    private final JTextField txtFiltroData;
    private final JComboBox<String> comboFiltroFilme;
    private final DefaultTableModel tabelaModel;
    private final JTable tabela;

    public PainelSessoes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.LIGHT_GRAY);

        // Filtros
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filtroPanel.setBackground(Color.LIGHT_GRAY);

        filtroPanel.add(new JLabel("Data:"));
        txtFiltroData = new JTextField(10);
        filtroPanel.add(txtFiltroData);

        filtroPanel.add(new JLabel("Filme:"));
        comboFiltroFilme = new JComboBox<>();
        comboFiltroFilme.addItem("Todos");
        DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .map(f -> f.getTitulo())
                .distinct()
                .sorted()
                .forEach(comboFiltroFilme::addItem);
        filtroPanel.add(comboFiltroFilme);

        // Ações dos filtros
        txtFiltroData.addActionListener(e -> atualizarTabela());
        comboFiltroFilme.addActionListener(e -> atualizarTabela());

        // Tabela
        String[] colunas = {"Data", "Hora", "Filme", "Sala"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };
        tabela = new JTable(tabelaModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Botões
        JButton btnAdicionar = new JButton("Adicionar Sessão");
        JButton btnMapa = new JButton("Mapa de Lugares");
        JButton btnEditar = new JButton("Editar");

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.add(btnAdicionar);
        botoesPanel.add(btnMapa);
        botoesPanel.add(btnEditar);

        btnAdicionar.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new PopupAdicionarSessao(parentFrame, this).setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                Sessao sessaoSelecionada = DadosApp.getInstance().getListaSessoes().getSessoes().get(selectedRow);
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                new PopupEditarSessao(parentFrame, this, sessaoSelecionada).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma sessão para editar.");
            }
        });

        btnMapa.addActionListener(e -> btnMostrarMapaActionPerformed());

        // Montar o painel
        add(filtroPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        atualizarTabela();
    }

    public void btnMostrarMapaActionPerformed() {
        int selectedRow = tabela.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dataStr = (String) tabela.getValueAt(selectedRow, 0);
        String horaStr = (String) tabela.getValueAt(selectedRow, 1);
        String titulo = (String) tabela.getValueAt(selectedRow, 2);
        String nomeSala = (String) tabela.getValueAt(selectedRow, 3);

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mostrarPopupLugaresDisponiveis(parentFrame, dataStr, horaStr, titulo, nomeSala);
    }

    private void mostrarPopupLugaresDisponiveis(JFrame parent, String dataStr, String horaStr, String titulo, String nomeSala) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

            Sessao sessaoSelecionada = DadosApp.getInstance().getListaSessoes().getSessoes().stream()
                    .filter(s -> s.getFilme().getTitulo().equals(titulo))
                    .filter(s -> s.getSala().getNome().equals(nomeSala))
                    .filter(s -> s.getData().format(df).equals(dataStr))
                    .filter(s -> s.getHorario().getInicio().format(tf).equals(horaStr))
                    .findFirst()
                    .orElse(null);

            if (sessaoSelecionada == null) {
                throw new Exception("Sessão não encontrada.");
            }

            new PopupLugaresDisponiveis(parent, sessaoSelecionada).setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir o mapa de lugares: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void atualizarTabela() {
        tabelaModel.setRowCount(0);
        String filtroData = txtFiltroData.getText().trim();
        String filtroFilme = (String) comboFiltroFilme.getSelectedItem();

        List<Sessao> sessoes = DadosApp.getInstance().getListaSessoes().getSessoes();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

        sessoes.stream()
                .filter(s -> filtroData.isEmpty() || s.getData().format(df).contains(filtroData))
                .filter(s -> filtroFilme.equals("Todos") || s.getFilme().getTitulo().equalsIgnoreCase(filtroFilme))
                .forEach(s -> tabelaModel.addRow(new Object[]{
                        s.getData().format(df),
                        s.getHorario().getInicio().format(tf),
                        s.getFilme().getTitulo(),
                        s.getSala().getNome()
                }));
    }
}
