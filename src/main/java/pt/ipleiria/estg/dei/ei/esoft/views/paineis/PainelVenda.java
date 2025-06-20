package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Bilhete;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaProdutos;
import pt.ipleiria.estg.dei.ei.esoft.views.popups.PopupConfigurarBilhete;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainelVenda extends JPanel implements IListener {

    private ListaProdutos listaProdutos;
    private List<String> categorias;

    private DefaultComboBoxModel<String> modelProdutos;
    private DefaultComboBoxModel<String> modelCategorias;

    private JComboBox<String> comboProduto;
    private JComboBox<String> comboCategoria;

    private JPanel painelProduto;
    private JLabel produtoNome;
    private JLabel produtoPreco;
    private JButton btnConfigurarBilhete;
    private JTextField produtoQty;

    public PainelVenda() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        listaProdutos = DadosApp.getInstance().getListaProdutos();

        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //filtrosPanel.setLayout(new BoxLayout(filtrosPanel, BoxLayout.X_AXIS));

        // Painel de filtro no topo
        JPanel produtoFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        produtoFilterPanel.setBackground(Color.LIGHT_GRAY);
        produtoFilterPanel.add(new JLabel("Produto"));
        modelProdutos = new DefaultComboBoxModel<>();
        comboProduto = new JComboBox<>(modelProdutos);
        produtoFilterPanel.add(comboProduto);

        comboProduto.addActionListener(e -> atualizarPainelProduto()); // Listener de filtro

        JPanel categoriaFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriaFilterPanel.setBackground(Color.LIGHT_GRAY);
        categoriaFilterPanel.add(new JLabel("Categoria"));
        modelCategorias = new DefaultComboBoxModel<>();
        comboCategoria = new JComboBox<>(modelCategorias);
        categoriaFilterPanel.add(comboCategoria);

        comboCategoria.addActionListener(e -> atualizarListaProdutos()); // Listener de filtro

        filtrosPanel.add(produtoFilterPanel);
        filtrosPanel.add(categoriaFilterPanel);

        // Lista visual com scroll

        // Botões verticais à direita
        JButton btnAddCarrinho = new JButton("Adicionar ao Carrinho");
        btnAddCarrinho.addActionListener(e -> adicionarCarrinho());

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        //botoesPanel.setBackground(Color.LIGHT_GRAY);
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAddCarrinho.setAlignmentX(Component.CENTER_ALIGNMENT);

        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(btnAddCarrinho);
        botoesPanel.add(Box.createHorizontalGlue());


        painelProduto = new JPanel();
        painelProduto.setLayout(new BoxLayout(painelProduto, BoxLayout.X_AXIS));
        painelProduto.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JPanel image = new JPanel();
        image.setSize(new Dimension(100, 100));
        image.setOpaque(true);
        image.setBackground(Color.LIGHT_GRAY);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        produtoNome = new JLabel("Bilhete simples");
        produtoNome.setFont(new Font(produtoNome.getFont().getName(), Font.BOLD, 16));

        JPanel priceQuantPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JButton minus = new JButton("-");
        minus.addActionListener(e -> produtoQty.setText(String.valueOf(Math.max(1, Integer.parseInt(produtoQty.getText()) - 1))));
        produtoQty = new JTextField("1", 2);
        JButton plus = new JButton("+");
        plus.addActionListener(e -> produtoQty.setText(String.valueOf(Integer.parseInt(produtoQty.getText()) + 1)));
        quantityPanel.add(minus);
        quantityPanel.add(produtoQty);
        quantityPanel.add(plus);

        produtoPreco = new JLabel("10€");


        infoPanel.add(produtoNome);
        priceQuantPanel.add(quantityPanel);
        priceQuantPanel.add(produtoPreco);
        infoPanel.add(priceQuantPanel);

        btnConfigurarBilhete = new JButton("Configurar Bilhete");
        btnConfigurarBilhete.setVisible(false);
        btnConfigurarBilhete.addActionListener(e -> btnConfigurarBilheteActionPerformed());


        painelProduto.add(image);
        painelProduto.add(infoPanel);
        painelProduto.add(Box.createHorizontalGlue());
        painelProduto.add(btnConfigurarBilhete);


        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(painelProduto);
        wrapper.add(Box.createVerticalGlue());


        // Junta lista e botões
        JPanel centroPanel = new JPanel(new BorderLayout());
        centroPanel.setBackground(Color.LIGHT_GRAY);
        centroPanel.add(botoesPanel, BorderLayout.SOUTH);
        centroPanel.add(wrapper, BorderLayout.CENTER);

        // Junta tudo ao painel
        add(filtrosPanel, BorderLayout.NORTH);
        add(centroPanel, BorderLayout.CENTER);
    }

    public void btnConfigurarBilheteActionPerformed() {
        Produto p = getProdutoSelecionado();
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        try {
            int quantidade = Integer.parseInt(produtoQty.getText());
            for (int i = 0; i < quantidade; i++) {
                Bilhete bilhete = new Bilhete(p);
                new PopupConfigurarBilhete(parentFrame, bilhete).setVisible(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDropDowns() {
        modelProdutos.removeAllElements();
        for (Produto produto : listaProdutos.getProdutos()) {
            modelProdutos.addElement(produto.getNome());
        }
        categorias = new ArrayList<>(listaProdutos.getCategorias());
        categorias.addFirst("Todas"); // Adiciona a opção "Todas" no início
        modelCategorias.removeAllElements();
        for (String categoria : categorias) {
            modelCategorias.addElement(categoria);
        }
        atualizarListaProdutos();
    }

    private void atualizarListaProdutos() {
        String produtoSelecionado = (String) comboProduto.getSelectedItem();
        modelProdutos.removeAllElements();
        String categoriaSelecionada = (String) comboCategoria.getSelectedItem();
        for (Produto produto : listaProdutos.getProdutos()) {
            if (categoriaSelecionada == null || categoriaSelecionada.equals("Todas") || produto.getCategoria().equals(categoriaSelecionada)) {
                if (produto.isAtivo() && produto.getStock() > 0)
                    modelProdutos.addElement(produto.getNome());
            }
        }
        if (modelProdutos.getSize() != 0) {
            if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
                comboProduto.setSelectedItem(produtoSelecionado);
            } else {
                comboProduto.setSelectedIndex(0); // Seleciona o primeiro item se nenhum estiver selecionado
            }
        }
    }

    private void atualizarPainelProduto() {
        String produtoSelecionado = (String) comboProduto.getSelectedItem();
        if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
            Produto produto = listaProdutos.getProdutos().stream()
                    .filter(p -> p.getNome().equals(produtoSelecionado))
                    .findFirst()
                    .orElse(null);
            if (produto != null) {
                produtoNome.setText(produto.getNome());
                produtoPreco.setText(String.format("%.2f€", produto.getPreco()));
                btnConfigurarBilhete.setVisible(isBilhete());

                // Aqui pode adicionar a imagem do produto se tiver
                // image.setIcon(new ImageIcon(produto.getImagePath()));
            }
        } else {
            produtoNome.setText("Selecione um produto");
            produtoPreco.setText("");
        }
    }

    private void adicionarCarrinho() {
        String produtoSelecionado = (String) comboProduto.getSelectedItem();
        if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
            Produto produto = listaProdutos.getProdutos().stream()
                    .filter(p -> p.getNome().equals(produtoSelecionado))
                    .findFirst()
                    .orElse(null);
            if (produto != null) {
                if (produto.getCategoria().equals("Bilhete")) {
                    JOptionPane.showMessageDialog(this, "Por favor, configure o bilhete antes de adicionar ao carrinho.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int quantidade = Integer.parseInt(produtoQty.getText());
                if (quantidade <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (produto.getStock() < DadosApp.getInstance().getCarrinho().getProdutosQuantidade().getOrDefault(produto, 0) + quantidade) {
                    JOptionPane.showMessageDialog(this, "Quantidade em stock insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < quantidade; i++) {
                    // Adiciona o produto ao carrinho a quantidade especificada
                    // Aqui pode verificar se o produto já existe no carrinho e atualizar a quantidade se necessário
                    // Por simplicidade, estamos apenas adicionando o produto diretamente
                    if (produto instanceof Bilhete bilhete) {
                        bilhete.getSessao().setLugarOcupado(bilhete.getNumeroFila(), bilhete.getNumeroLugar(), true);
                    }
                    DadosApp.getInstance().getCarrinho().adicionarItem(produto);
                }
                JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho!");
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean isBilhete() {
        String produtoSelecionado = (String) comboProduto.getSelectedItem();
        if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
            Produto produto = listaProdutos.getProdutos().stream()
                    .filter(p -> p.getNome().equals(produtoSelecionado))
                    .findFirst()
                    .orElse(null);
            return produto != null && produto.getCategoria().equals("Bilhete");
        }
        return false;
    }

    private Produto getProdutoSelecionado() {
        String produtoSelecionado = (String) comboProduto.getSelectedItem();
        if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
            return listaProdutos.getProdutos().stream()
                    .filter(p -> p.getNome().equals(produtoSelecionado))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public void update() {
        atualizarDropDowns();
    }
}
