package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.classes.Sala;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaSalas;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupAdicionarSala;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupDetalhesSala;
import pt.ipleiria.estg.dei.ei.esoft.DadosApp;

import javax.swing.*;
import java.awt.*;

/**
 * Painel principal responsável por listar as salas e permitir ações como adicionar ou consultar detalhes.
 */
public class PainelSalas extends JPanel {

    private final DefaultListModel<Sala> listaModel = new DefaultListModel<>();

    private JComboBox<String> comboFiltro;
    private JList<Sala> listaSalasJList;

    /**
     * Construtor do painel de salas
     */
    public PainelSalas() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Painel de filtro no topo
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.LIGHT_GRAY);
        filtroPanel.add(new JLabel("Filtro"));
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Ativos", "Inativos"});
        filtroPanel.add(comboFiltro);

        comboFiltro.addActionListener(e -> atualizarLista()); // Listener de filtro

        // Lista visual com scroll
        listaSalasJList = new JList<>(listaModel);
        listaSalasJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaSalasJList.setBackground(Color.WHITE);
        listaSalasJList.setSelectionBackground(Color.BLACK);
        listaSalasJList.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listaSalasJList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Botões verticais à direita
        JButton btnAdicionarSala = new JButton("Adicionar Sala");
        JButton btnDetalhes = new JButton("Detalhes");

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAdicionarSala.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDetalhes.setAlignmentX(Component.CENTER_ALIGNMENT);

        botoesPanel.add(Box.createVerticalGlue());
        botoesPanel.add(btnAdicionarSala);
        botoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        botoesPanel.add(btnDetalhes);
        botoesPanel.add(Box.createVerticalGlue());

        // Ação do botão "Adicionar Sala"
        btnAdicionarSala.addActionListener(e -> btnAdicionarSalaActionPerformed());


        // Ação do botão "Detalhes"
        btnDetalhes.addActionListener(e -> {
            int index = listaSalasJList.getSelectedIndex();
            if (index != -1) {
                Sala salaSelecionada = listaSalasJList.getSelectedValue();
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                new PopupDetalhesSala(parentFrame, salaSelecionada).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma sala da lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Junta lista e botões
        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.setBackground(Color.LIGHT_GRAY);
        centroPanel.add(scrollPane, BorderLayout.CENTER);
        centroPanel.add(botoesPanel, BorderLayout.EAST);

        // Junta tudo ao painel
        add(filtroPanel, BorderLayout.NORTH);
        add(centroPanel, BorderLayout.CENTER);

        atualizarLista(); // Inicializa lista com todas as salas
    }


    public void btnAdicionarSalaActionPerformed() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        mostrarPopupAdicionarSala(parentFrame);
    }

    private void mostrarPopupAdicionarSala(JFrame parentFrame) {
        PopupAdicionarSala popup = new PopupAdicionarSala(parentFrame, this);
        popup.setVisible(true);
    }

    /**
     * Atualiza a lista de salas com base no filtro selecionado
     * publico para ser usado no popup
     */
    public void atualizarLista() {
        listaModel.clear();
        String filtro = (String) comboFiltro.getSelectedItem();

        for (Sala sala : DadosApp.getInstance().getListaSalas().getSalas()) {
            boolean mostrar = switch (filtro) {
                case "Ativos" -> sala.isAtiva();
                case "Inativos" -> !sala.isAtiva();
                default -> true;
            };
            if (mostrar) {
                listaModel.addElement(sala);
            }
        }
    }
}