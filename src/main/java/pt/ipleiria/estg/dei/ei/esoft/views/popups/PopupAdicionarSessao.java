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

/**
 * Popup que permite adicionar uma nova sessão de cinema.
 */
public class PopupAdicionarSessao extends JDialog {

    private final JComboBox<String> comboFilmes;
    private final JComboBox<String> comboSalas;
    private final JTextField txtData;
    private final JTextField txtHora;
    private final JTextField txtDuracao;

    public PopupAdicionarSessao(JFrame parent, PainelSessoes painelSessoes) {
        super(parent, "Nova Sessão", true);
        setLayout(new BorderLayout(10, 10));

        comboFilmes = new JComboBox<>();
        comboSalas = new JComboBox<>();
        txtData = new JTextField("dd-MM-yyyy");
        txtHora = new JTextField("hh:mm");
        txtDuracao = new JTextField();
        txtDuracao.setEditable(false);

        // Preenche filmes alugados
        DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .filter(Filme::isAtivo)
                .sorted((f1, f2) -> f1.getTitulo().compareToIgnoreCase(f2.getTitulo()))
                .forEach(f -> comboFilmes.addItem(f.getTitulo()));

        // Atualiza duração ao selecionar filme
        comboFilmes.addActionListener(e -> {
            String titulo = (String) comboFilmes.getSelectedItem();
            Filme filme = DadosApp.getInstance().getListaFilmes().getFilmeByTitulo(titulo);
            if (filme != null) {
                int duracao = filme.getDuracao();
                txtDuracao.setText(duracao / 60 + "h" + (duracao % 60) + "m");
            } else {
                txtDuracao.setText("");
            }
            atualizarSalasDisponiveis();
        });

        if (comboFilmes.getItemCount() > 0) {
            comboFilmes.setSelectedIndex(0); // Força trigger do actionListener para preencher duração e salas
        }

        // Atualiza lista de salas ao mudar data/hora
        txtData.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
        });
        txtHora.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarSalasDisponiveis(); }
        });

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.add(new JLabel("Filme a exibir")); form.add(comboFilmes);
        form.add(new JLabel("Data")); form.add(txtData);
        form.add(new JLabel("Hora")); form.add(txtHora);
        form.add(new JLabel("Sala")); form.add(comboSalas);
        form.add(new JLabel("Duração")); form.add(txtDuracao);

        JButton btnAdicionar = new JButton("Adicionar Sessão");
        btnAdicionar.addActionListener(e -> {
            try {
                String titulo = (String) comboFilmes.getSelectedItem();
                Filme filme = DadosApp.getInstance().getListaFilmes().getFilmeByTitulo(titulo);
                if (filme == null) throw new Exception("Filme inválido");

                // 1. Validar Data e Hora
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                Horario horario = new Horario(hora, hora.plusMinutes(filme.getDuracao()));


                // 2. Verificar se o filme está dentro da validade da licença
                LocalDate inicioLicenca = filme.getDataAluguer();
                LocalDate fimLicenca = inicioLicenca.plusDays(filme.getDuracaoLicencaDias());
                if (data.isBefore(inicioLicenca) || data.isAfter(fimLicenca)) {
                    JOptionPane.showMessageDialog(this,
                            "A sessão está fora do período de licença do filme.\n" +
                                    "Licença válida de " + inicioLicenca + " até " + fimLicenca + ".",
                            "Licença Expirada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 3. Validar Sala
                String nomeSala = (String) comboSalas.getSelectedItem();
                Sala sala = DadosApp.getInstance().getListaSalas().getSalaByNome(nomeSala);
                if (sala == null || !sala.isAtiva()) {
                    JOptionPane.showMessageDialog(this, "Sala inválida ou inativa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 4. Verificar compatibilidade da sala com características do filme
                if (filme.isIs3D() && !sala.isDolbyAtmos()) {
                    JOptionPane.showMessageDialog(this, "O filme requer uma sala compatível com Dolby Atmos (3D).", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 5. Verificar disponibilidade da sala no horário
                if (!DadosApp.getInstance().getListaSessoes().isSalaDisponivel(sala, data, horario)) {
                    JOptionPane.showMessageDialog(this, "Sala não disponível para este horário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Se tudo estiver OK, cria a sessão
                Sessao nova = new Sessao(filme, data, horario, sala);
                DadosApp.getInstance().getListaSessoes().addToEndOfList(nova);
                JOptionPane.showMessageDialog(this, "Sessão adicionada!");
                painelSessoes.atualizarTabela();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos. Verifique todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);

        add(form, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void atualizarSalasDisponiveis() {
        comboSalas.removeAllItems();
        try {
            String titulo = (String) comboFilmes.getSelectedItem();
            Filme filme = DadosApp.getInstance().getListaFilmes().getFilmeByTitulo(titulo);
            if (filme == null) return;

            LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            Horario horario = new Horario(hora, hora.plusMinutes(filme.getDuracao()));

            List<Sala> salasDisponiveis = DadosApp.getInstance().getListaSalas().getSalas().stream()
                    .filter(Sala::isAtiva)
                    //.filter(sala -> !filme.isIs3D() || sala.())
                    .filter(sala -> DadosApp.getInstance().getListaSessoes().isSalaDisponivel(sala, data, horario))
                    .toList();

            for (Sala sala : salasDisponiveis) {
                comboSalas.addItem(sala.getNome());
            }

//            System.out.println("---- Atualizar Salas ----");
//            System.out.println("Filme: " + filme.getTitulo());
//            System.out.println("Data: " + data);
//            System.out.println("Hora: " + hora);
//            System.out.println("Duracao: " + filme.getDuracao());
//            System.out.println("Fim: " + horario.getFim());
//            System.out.println("Salas totais: " + DadosApp.getInstance().getListaSalas().getSalas().size());

        } catch (Exception ignored) {
            // Campos ainda não válidos, não fazer nada
        }
    }
}
