package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Carrinho;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.classes.utils.IListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PopupCarrinho extends JDialog implements IListener {
    private DefaultListModel<String> productModel;
    private JList<String> cartList;
    private JLabel finalPriceLabel;
    private java.util.List<Double> prices;

    private Carrinho carrinho;

    public PopupCarrinho(JFrame parent) {
        super(parent, "Carrinho", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        productModel = new DefaultListModel<>();
        prices = new ArrayList<>();
        finalPriceLabel = new JLabel("", SwingConstants.CENTER);
        carrinho = DadosApp.getInstance().getCarrinho();
        carrinho.setListener(this);

        cartList = new JList<>(productModel);
        JScrollPane scrollPane = new JScrollPane(cartList);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JButton removeButton = new JButton("Remover");
        removeButton.addActionListener(e -> removeSelectedItem());

        JButton confirmButton = new JButton("Comprar");
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Purchase confirmed!");
            dispose();
        });

        // Right-side layout with BoxLayout
        Box rightBox = Box.createVerticalBox();
        rightBox.add(Box.createVerticalGlue());
        rightBox.add(removeButton);
        rightBox.add(Box.createVerticalGlue());
        rightBox.add(finalPriceLabel);
        rightBox.add(Box.createVerticalGlue());
        rightBox.add(confirmButton);
        rightBox.add(Box.createVerticalGlue());

        rightPanel.add(rightBox, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(rightPanel, BorderLayout.EAST);

        updateLista();
    }

    private void removeSelectedItem() {
        int index = cartList.getSelectedIndex();
        if (index >= 0) {
            carrinho.removeItem(index);
        }
    }

    private void updateLista() {
        productModel.clear();
        for (Produto produto : carrinho.getProdutos()) {
            productModel.addElement(produto.getNome() + " - " + String.format("%.2f", produto.getPreco()) + "€");
        }
        finalPriceLabel.setText("Preço Final:\n " + String.format("%.2f", carrinho.getFinalPrice()) + "€");
    }

    @Override
    public void update() {
        updateLista();
    }
}
