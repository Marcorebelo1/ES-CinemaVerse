package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupAdicionarProduto;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupEditarProduto;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaProdutos;


import javax.swing.*;
import java.awt.*;


public class PainelStock extends JPanel implements IListener {

    private ListaProdutos listaProdutos;
    private JList<Produto> listaProdutosJList;
    private DefaultListModel<Produto> model;
    private JLabel lblNome, lblCategoria, lblPreco, lblStock, lblEstado;
    private JComboBox<String> comboCategoria;

    public PainelStock() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Topo: Título
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topo.setBackground(Color.LIGHT_GRAY);

        // Filtro de categoria
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Todas");
        for (String cat : DadosApp.getInstance().getCategorias()) {
            comboCategoria.addItem(cat);
        }
        topo.add(new JLabel("Categoria:"));
        topo.add(comboCategoria);

        // Lista de produtos
        model = new DefaultListModel<>();
        listaProdutos = DadosApp.getInstance().getListaProdutos();
        listaProdutosJList = new JList<>(model);
        listaProdutosJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaProdutosJList.setBackground(Color.WHITE);
        listaProdutosJList.setSelectionBackground(Color.BLACK);
        listaProdutosJList.setSelectionForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listaProdutosJList);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Detalhes do produto selecionado
        JPanel detalhesPanel = new JPanel();
        detalhesPanel.setLayout(new BoxLayout(detalhesPanel, BoxLayout.Y_AXIS));
        detalhesPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Produto"));
        detalhesPanel.setBackground(Color.LIGHT_GRAY);

        lblNome = new JLabel("Nome: ");
        lblCategoria = new JLabel("Categoria: ");
        lblPreco = new JLabel("Preço: ");
        lblStock = new JLabel("Stock: ");
        lblEstado = new JLabel("Estado: ");

        detalhesPanel.add(lblNome);
        detalhesPanel.add(lblCategoria);
        detalhesPanel.add(lblPreco);
        detalhesPanel.add(lblStock);
        detalhesPanel.add(lblEstado);

        // Botões
        JButton btnAdicionar = new JButton("Adicionar Produto");
        JButton btnEditar = new JButton("Editar Produto");

        btnAdicionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEditar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAdicionar.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            new PopupAdicionarProduto(parentFrame, this).setVisible(true);
        });

        btnEditar.addActionListener(e -> {
            Produto produtoSelecionado = listaProdutosJList.getSelectedValue();
            if (produtoSelecionado != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                new PopupEditarProduto(parentFrame, this, produtoSelecionado).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto da lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoesPanel.add(Box.createVerticalGlue());
        botoesPanel.add(btnAdicionar);
        botoesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        botoesPanel.add(btnEditar);
        botoesPanel.add(Box.createVerticalGlue());

        // Centro: lista + detalhes + botões
        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.setBackground(Color.LIGHT_GRAY);
        centroPanel.add(scrollPane, BorderLayout.WEST);
        centroPanel.add(detalhesPanel, BorderLayout.CENTER);
        centroPanel.add(botoesPanel, BorderLayout.EAST);

        add(topo, BorderLayout.NORTH);
        add(centroPanel, BorderLayout.CENTER);

        listaProdutosJList.addListSelectionListener(e -> mostrarDetalhes());

        comboCategoria.addActionListener(e -> atualizarLista());
        atualizarLista();
    }


    public void atualizarLista() {
        model.clear();
        String categoriaSelecionada = (String) comboCategoria.getSelectedItem();
        for (Produto produto : DadosApp.getInstance().getListaProdutos().getProdutos()) {
            if ("Todas".equals(categoriaSelecionada) || produto.getCategoria().equals(categoriaSelecionada)) {
                model.addElement(produto);
            }
        }
        mostrarDetalhes();
    }

    private void mostrarDetalhes() {
        Produto produto = listaProdutosJList.getSelectedValue();
        if (produto != null) {
            lblNome.setText("Nome: " + produto.getNome());
            lblCategoria.setText("Categoria: " + produto.getCategoria());
            lblPreco.setText(String.format("Preço: %.2f€", produto.getPreco()));
            // Supondo que Produto tem getStock(), senão ajuste conforme necessário
            lblStock.setText("Stock: " + (produto.getStock() != 0 ? produto.getStock() : "N/A"));
            lblEstado.setText("Estado: " + (produto.isAtivo() ? "Ativo" : "Inativo"));

        } else {
            lblNome.setText("Nome: ");
            lblCategoria.setText("Categoria: ");
            lblPreco.setText("Preço: ");
            lblStock.setText("Stock: ");
            lblEstado.setText("Estado: ");
        }
    }

    @Override
    public void update() {
        atualizarLista();
    }
}