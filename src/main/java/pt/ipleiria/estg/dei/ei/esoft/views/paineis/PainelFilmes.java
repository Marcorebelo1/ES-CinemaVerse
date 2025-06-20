package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupAlugarFilme;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupDetalhesFilme;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Painel responsável por listar filmes alugados, com filtro por categoria e 3D.
 */
public class PainelFilmes extends JPanel {

    private final DefaultListModel<Filme> listaModel = new DefaultListModel<>();
    private JComboBox<String> comboCategoria;
    private JComboBox<String> combo3D;
    private JList<Filme> listaFilmes;

    public PainelFilmes() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Topo: Filtros
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.LIGHT_GRAY);
        filtroPanel.add(new JLabel("Categoria"));

        comboCategoria = new JComboBox<>(new String[]{"Todos", "Ação", "Comédia", "Terror", "Infantil", "Ficção", "Drama"});
        filtroPanel.add(comboCategoria);

        combo3D = new JComboBox<>(new String[]{"Todos", "Sim", "Não"});
        filtroPanel.add(new JLabel("3D"));
        filtroPanel.add(combo3D);

        JLabel titulo = new JLabel("Filmes Adquiridos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        filtroPanel.add(Box.createHorizontalStrut(20));
        filtroPanel.add(titulo);

        comboCategoria.addActionListener(e -> atualizarLista());
        combo3D.addActionListener(e -> atualizarLista());

        // Centro: Lista de Filmes
        listaFilmes = new JList<>(listaModel);
        listaFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFilmes.setBackground(Color.WHITE);
        listaFilmes.setSelectionBackground(Color.BLACK);
        listaFilmes.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listaFilmes);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Botões laterais
        JButton btnAlugarFilmes = new JButton("Alugar filme");
        JButton btnDetalhes = new JButton("Detalhes");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAlugarFilmes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDetalhes.setAlignmentX(Component.CENTER_ALIGNMENT);

        botoesPanel.add(Box.createVerticalGlue());
        botoesPanel.add(btnAlugarFilmes);
        botoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        botoesPanel.add(btnDetalhes);
        botoesPanel.add(Box.createVerticalGlue());

        btnAlugarFilmes.addActionListener(e -> btnAlugarFilmesActionPerformed());

        btnDetalhes.addActionListener(e -> {
            int index = listaFilmes.getSelectedIndex();
            if (index != -1) {
                Filme filmeSelecionado = listaFilmes.getSelectedValue();
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                new PopupDetalhesFilme(parentFrame, filmeSelecionado).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um filme da lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Junta tudo
        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.setBackground(Color.LIGHT_GRAY);
        centroPanel.add(scrollPane, BorderLayout.CENTER);
        centroPanel.add(botoesPanel, BorderLayout.EAST);

        add(filtroPanel, BorderLayout.NORTH);
        add(centroPanel, BorderLayout.CENTER);

        atualizarLista();
    }

    public void btnAlugarFilmesActionPerformed() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mostrarPopupAlugarFilme(parentFrame);
    }

    private void mostrarPopupAlugarFilme(JFrame parentFrame) {
        new PopupAlugarFilme(parentFrame, this).setVisible(true);
    }


    /**
     * Atualiza a lista de filmes com base nos filtros.
     * Público para ser chamado externamente
     */
    public void atualizarLista() {
        listaModel.clear();

        String categoriaFiltro = (String) comboCategoria.getSelectedItem();
        String filtro3D = (String) combo3D.getSelectedItem();

        List<Filme> filmes = DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .filter(f -> f.isAtivo()) // apenas filmes com licença ativa
                .filter(f -> categoriaFiltro.equals("Todos") || f.getCategoria().equalsIgnoreCase(categoriaFiltro))
                .filter(f -> filtro3D.equals("Todos") || (filtro3D.equals("Sim") == f.isIs3D()))
                .sorted((f1, f2) -> f1.getTitulo().compareToIgnoreCase(f2.getTitulo())) // ordem alfabética
                .collect(Collectors.toList());

        filmes.forEach(listaModel::addElement);
    }
}
