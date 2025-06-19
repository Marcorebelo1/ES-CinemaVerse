package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelFilmes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Popup para alugar novo filme.
 */
public class PopupAlugarFilme extends JDialog {

    public PopupAlugarFilme(JFrame parentFrame, PainelFilmes painelFilmes) {
        super(parentFrame, "Alugar Filme", true);
        setLayout(new BorderLayout(10, 10));

        JComboBox<String> comboTitulos = new JComboBox<>();
        comboTitulos.setEditable(true);
        List<String> alugadosAtivos = DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .filter(Filme::isAtivo)
                .map(Filme::getTitulo)
                .toList();
        DadosApp.getInstance().getFilmesCatalogo().stream()
                .map(Filme::getTitulo)
                .filter(titulo -> !alugadosAtivos.contains(titulo))
                .sorted()
                .forEach(comboTitulos::addItem);



        JTextField txtCategoria = new JTextField();
        JTextField txtDuracao = new JTextField();
        JTextField txtIdade = new JTextField();
        JTextField txtFornecedor = new JTextField();
        JTextField txtNSemanas = new JTextField("1");
        JTextField txtPreco = new JTextField("2000.00€");
        txtPreco.setEditable(false);

        JRadioButton rb1Semana = new JRadioButton("1 Semana");
        JRadioButton rbXSemanas = new JRadioButton("N Semanas");
        JRadioButton rb1Mes = new JRadioButton("1 Mês");
        ButtonGroup grupoLicenca = new ButtonGroup();
        grupoLicenca.add(rb1Semana);
        grupoLicenca.add(rbXSemanas);
        grupoLicenca.add(rb1Mes);
        rb1Semana.setSelected(true);

        JRadioButton rbOriginal = new JRadioButton("Original");
        JRadioButton rbDublada = new JRadioButton("Dublada");
        ButtonGroup grupoVersao = new ButtonGroup();
        grupoVersao.add(rbOriginal);
        grupoVersao.add(rbDublada);
        rbOriginal.setSelected(true);

        JRadioButton rb3DSim = new JRadioButton("Sim");
        JRadioButton rb3DNao = new JRadioButton("Não");
        ButtonGroup grupo3D = new ButtonGroup();
        grupo3D.add(rb3DSim);
        grupo3D.add(rb3DNao);
        rb3DSim.setSelected(true);

        comboTitulos.addActionListener(e -> {
            String tituloSelecionado = (String) comboTitulos.getSelectedItem();
            Filme f = DadosApp.getInstance().getFilmeDoCatalogo(tituloSelecionado);
            if (f != null) {
                txtCategoria.setText(f.getCategoria());
                txtCategoria.setEditable(false);
                txtDuracao.setText(String.valueOf(f.getDuracao()));
                txtDuracao.setEditable(false);
                txtIdade.setText(f.getClassificacaoEtaria());
                txtIdade.setEditable(false);
                txtFornecedor.setText(f.getFornecedor());
                txtFornecedor.setEditable(false);
            } else {
                txtCategoria.setText("");
                txtCategoria.setEditable(true);
                txtDuracao.setText("");
                txtDuracao.setEditable(true);
                txtIdade.setText("");
                txtIdade.setEditable(true);
                txtFornecedor.setText("");
                txtFornecedor.setEditable(true);
            }
        });

        //Para pré carregar os dados do 1ºFilme
        if (comboTitulos.getItemCount() > 0) {
            comboTitulos.setSelectedIndex(0);
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Título Disponível:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboTitulos, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCategoria, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Duração (min):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDuracao, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Idade Recomendada:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdade, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Fornecedor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtFornecedor, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Duração da Licença:"), gbc);
        gbc.gridx = 1;
        JPanel licencaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        licencaPanel.add(rb1Semana);
        licencaPanel.add(rbXSemanas);
        licencaPanel.add(txtNSemanas);
        licencaPanel.add(rb1Mes);
        formPanel.add(licencaPanel, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Versão:"), gbc);
        gbc.gridx = 1;
        JPanel versaoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versaoPanel.add(rbOriginal);
        versaoPanel.add(rbDublada);
        formPanel.add(versaoPanel, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("3D:"), gbc);
        gbc.gridx = 1;
        JPanel d3Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        d3Panel.add(rb3DSim);
        d3Panel.add(rb3DNao);
        formPanel.add(d3Panel, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPreco, gbc); y++;

        JButton btnAlugar = new JButton("Alugar");
        btnAlugar.addActionListener(e -> {
            try {
                String titulo = comboTitulos.getSelectedItem().toString().trim();
                Filme base = DadosApp.getInstance().getFilmeDoCatalogo(titulo);

                String categoria;
                int duracao;
                String idade;
                String fornecedor;

                if (base != null) {
                    categoria = base.getCategoria();
                    duracao = base.getDuracao();
                    idade = base.getClassificacaoEtaria();
                    fornecedor = base.getFornecedor();
                } else {
                    categoria = txtCategoria.getText().trim();
                    duracao = Integer.parseInt(txtDuracao.getText().trim());
                    idade = txtIdade.getText().trim();
                    fornecedor = txtFornecedor.getText().trim();
                }

                int diasLicenca = 7;
                if (rb1Mes.isSelected()) {
                    diasLicenca = 30;
                } else if (rbXSemanas.isSelected()) {
                    diasLicenca = Integer.parseInt(txtNSemanas.getText().trim()) * 7;
                }

                String versao = rbOriginal.isSelected() ? "Original" : "Dublada";
                boolean is3D = rb3DSim.isSelected();

                Filme novoFilme = new Filme(titulo, duracao, idade, categoria, versao, is3D, fornecedor, diasLicenca, LocalDate.now());

                boolean sucesso = DadosApp.getInstance().getListaFilmes().addToEndOfList(novoFilme);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Filme alugado com sucesso!");
                    painelFilmes.atualizarLista();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao guardar filme.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Campos inválidos. Verifique os dados inseridos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAlugar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 500);
        setLocationRelativeTo(parentFrame);
    }
}