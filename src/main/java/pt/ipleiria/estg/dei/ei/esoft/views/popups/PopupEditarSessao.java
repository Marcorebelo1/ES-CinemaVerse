package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.*;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelSessoes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PopupEditarSessao extends JDialog {
    private final JComboBox<String> comboFilmes;
    private final JComboBox<String> comboSalas;
    private final JTextField txtData;
    private final JTextField txtHora;

    public PopupEditarSessao(JFrame parent, PainelSessoes painelSessoes, Sessao sessaoOriginal) {
        super(parent, "Sessão Selecionada", true);
        setLayout(new BorderLayout(10, 10));

        comboFilmes = new JComboBox<>();
        comboSalas = new JComboBox<>();
        txtData = new JTextField(sessaoOriginal.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        txtHora = new JTextField(sessaoOriginal.getHorario().getInicio().format(DateTimeFormatter.ofPattern("HH:mm")));

        // Preencher filmes
        DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .filter(Filme::isAtivo)
                .sorted((f1, f2) -> f1.getTitulo().compareToIgnoreCase(f2.getTitulo()))
                .forEach(f -> comboFilmes.addItem(f.getTitulo()));
        comboFilmes.setSelectedItem(sessaoOriginal.getFilme().getTitulo());

        // Preencher salas
        DadosApp.getInstance().getListaSalas().getSalas().stream()
                .filter(Sala::isAtiva)
                .forEach(s -> comboSalas.addItem(s.getNome()));
        comboSalas.setSelectedItem(sessaoOriginal.getSala().getNome());

        // Formulário
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.add(new JLabel("Filme")); form.add(comboFilmes);
        form.add(new JLabel("Data")); form.add(txtData);
        form.add(new JLabel("Hora")); form.add(txtHora);
        form.add(new JLabel("Sala")); form.add(comboSalas);

        JButton btnModificarSessao = new JButton("Modificar Sessão");
        btnModificarSessao.addActionListener(e -> btnModificarSessaoActionPerformed(painelSessoes, sessaoOriginal));

        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(e -> btnRemoverSessaoActionPerformed(painelSessoes, sessaoOriginal));


        JPanel botoes = new JPanel();
        botoes.add(btnModificarSessao);
        botoes.add(btnRemover);

        add(form, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocationRelativeTo(parent);
    }

    private void btnModificarSessaoActionPerformed(PainelSessoes painelSessoes, Sessao sessaoOriginal) {
        try {
            if (sessaoOriginal.temBilhete()) {
                JOptionPane.showMessageDialog(this, "Não é possível modificar uma sessão com bilhetes vendidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String titulo = (String) comboFilmes.getSelectedItem();
            Filme filme = DadosApp.getInstance().getListaFilmes().getFilmeByTitulo(titulo);
            if (filme == null) throw new Exception("Filme inválido");

            LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            Horario horario = new Horario(hora, hora.plusMinutes(filme.getDuracao()));

            if (data.isAfter(filme.getDataAluguer().plusDays(filme.getDuracaoLicencaDias()))) {
                JOptionPane.showMessageDialog(this, "O filme já não está dentro do período de licença.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nomeSala = (String) comboSalas.getSelectedItem();
            Sala sala = DadosApp.getInstance().getListaSalas().getSalaByNome(nomeSala);
            if (sala == null || !sala.isAtiva()) {
                JOptionPane.showMessageDialog(this, "Sala inválida ou inativa.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sessao nova = new Sessao(filme, data, horario, sala);
            boolean conflito = DadosApp.getInstance().getListaSessoes().getSessoes().stream()
                    .filter(s -> !s.equals(sessaoOriginal))
                    .filter(s -> s.getSala().equals(sala))
                    .filter(s -> s.getData().equals(data))
                    .anyMatch(s -> s.getHorario().conflitaCom(horario));

            if (conflito) {
                JOptionPane.showMessageDialog(this, "Já existe uma sessão nessa sala e horário!", "Erro de Conflito", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Atualiza a sessão original
            boolean response = sessaoOriginal.atualizarSessao(filme, data, horario, sala);
            if (response) {
                JOptionPane.showMessageDialog(this, "Sessão modificada com sucesso!");
                painelSessoes.atualizarTabela();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar a sessão.", "Erro", JOptionPane.ERROR_MESSAGE);
            }


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dados inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRemoverSessaoActionPerformed(PainelSessoes painelSessoes, Sessao sessaoOriginal) {
        DadosApp.getInstance().getListaSessoes().removerSessao(sessaoOriginal);
        JOptionPane.showMessageDialog(this, "Sessão removida.");
        painelSessoes.atualizarTabela();
        dispose();
    }

}