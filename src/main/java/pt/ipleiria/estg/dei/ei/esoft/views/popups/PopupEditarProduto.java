// src/main/java/pt/ipleiria/estg/dei/ei/esoft/views/popups/PopupEditarProduto.java
package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Produto;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelStock;
import javax.swing.*;
import java.awt.*;

public class PopupEditarProduto extends JDialog {
    public PopupEditarProduto(JFrame parentFrame, PainelStock painelStock, Produto produto) {
        super(parentFrame, "Editar Produto", true);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCategoria = new JLabel("Categoria:");
        JLabel lblPreco = new JLabel("Preço:");
        JLabel lblStock = new JLabel("Stock:");
        JLabel lblEstado = new JLabel("Estado:");



        JTextField txtNome = new JTextField();
        JComboBox<String> comboCategoria = new JComboBox<>(DadosApp.getInstance().getCategorias().toArray(new String[0]));
        JTextField txtPreco = new JTextField();
        JTextField txtStock = new JTextField();
        JLabel txtEstado = new JLabel(produto.isAtivo() ? "Ativar" : "Inativar");



        formPanel.add(lblNome); formPanel.add(txtNome);
        formPanel.add(lblCategoria); formPanel.add(comboCategoria);
        formPanel.add(lblPreco); formPanel.add(txtPreco);
        formPanel.add(lblStock); formPanel.add(txtStock);
        formPanel.add(lblEstado); formPanel.add(txtEstado);


        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnInativarAtivar = new JButton(produto.isAtivo() ? "Inativar" : "Ativar");



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

        btnInativarAtivar.addActionListener(e -> {
            boolean novoEstado = !produto.isAtivo();
            produto.setEstado(novoEstado);
            txtEstado.setText(novoEstado ? "Ativo" : "Inativo");
            btnInativarAtivar.setText(novoEstado ? "Inativar" : "Ativar");
            JOptionPane.showMessageDialog(this,
                    novoEstado ? "Produto reativado com sucesso!" : "Produto inativado com sucesso!",
                    "Estado do Produto", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirmar);
        buttonPanel.add(btnInativarAtivar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocationRelativeTo(parentFrame);
    }
}