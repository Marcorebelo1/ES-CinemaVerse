// src/main/java/pt/ipleiria/estg/dei/ei/esoft/views/popups/PopupAdicionarProduto.java
package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Bilhete;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelStock;

import javax.swing.*;
import java.awt.*;

public class PopupAdicionarProduto extends JDialog {
    public PopupAdicionarProduto(JFrame parentFrame, PainelStock painelStock) {
        super(parentFrame, "Adicionar Produto", true);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCategoria = new JLabel("Categoria:");
        JLabel lblPreco = new JLabel("Preço:");
        JLabel lblStock = new JLabel("Stock:");

        JTextField txtNome = new JTextField();
        JComboBox<String> comboCategoria = new JComboBox<>(DadosApp.getInstance().getCategorias().toArray(new String[0]));
        JTextField txtPreco = new JTextField();
        JTextField txtStock = new JTextField();

        formPanel.add(lblNome);
        formPanel.add(txtNome);
        formPanel.add(lblCategoria);
        formPanel.add(comboCategoria);
        formPanel.add(lblPreco);
        formPanel.add(txtPreco);
        formPanel.add(lblStock);
        formPanel.add(txtStock);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String categoria = comboCategoria.getSelectedItem().toString();
                double preco = Double.parseDouble(txtPreco.getText());
                int stock = Integer.parseInt(txtStock.getText());

                if (nome.isEmpty() || categoria.isEmpty() || preco < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Produto novoProduto = new Produto(nome, categoria, preco, stock);
                boolean sucesso = DadosApp.getInstance().getListaProdutos().adicionarProduto(novoProduto);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Produto criado com sucesso!");
                    painelStock.atualizarLista();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao guardar o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço e stock devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirmar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocationRelativeTo(parentFrame);
    }
}