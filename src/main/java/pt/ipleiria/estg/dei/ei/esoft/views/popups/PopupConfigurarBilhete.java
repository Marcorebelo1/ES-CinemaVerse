package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.*;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PopupConfigurarBilhete extends JDialog {
    private final JComboBox<String> comboFilmes;
    private final JComboBox<String> comboSessoes;
    private final JPanel painelLugares;
    private JButton[][] lugaresBtns;

    private Sessao sessaoSelecionada;
    private int filaSelecionada = -1;
    private int lugarSelecionado = -1;

    public PopupConfigurarBilhete(JFrame parent) {
        super(parent, "Configurar Bilhete", true);
        setLayout(new BorderLayout(10, 10));

        comboFilmes = new JComboBox<>();
        comboSessoes = new JComboBox<>();
        painelLugares = new JPanel();

        // Preenche filmes ativos
        DadosApp.getInstance().getListaFilmes().getFilmes().stream()
                .filter(Filme::isAtivo)
                .map(Filme::getTitulo)
                .distinct()
                .sorted()
                .forEach(comboFilmes::addItem);

        comboFilmes.addActionListener(e -> atualizarSessoes());
        comboSessoes.addActionListener(e -> mostrarDisposicaoSalaDaSessao());

        JPanel topo = new JPanel(new GridLayout(2, 2, 10, 10));
        topo.add(new JLabel("Filme"));
        topo.add(comboFilmes);
        topo.add(new JLabel("Sessão"));
        topo.add(comboSessoes);

        JScrollPane scroll = new JScrollPane(painelLugares);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> confirmarLugar());

        JPanel botoes = new JPanel();
        botoes.add(btnConfirmar);

        add(topo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void atualizarSessoes() {
        comboSessoes.removeAllItems();
        String titulo = (String) comboFilmes.getSelectedItem();
        List<Sessao> sessoes = DadosApp.getInstance().getListaSessoes().getSessoes().stream()
                .filter(s -> s.getFilme().getTitulo().equals(titulo))
                .toList();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

        for (Sessao s : sessoes) {
            String desc = s.getData().format(df) + " - " + s.getHorario().getInicio().format(tf) + " - Sala: " + s.getSala().getNome();
            comboSessoes.addItem(desc);
        }

        if (comboSessoes.getItemCount() > 0) {
            comboSessoes.setSelectedIndex(0); // força visualização
        }
    }

    private void mostrarDisposicaoSalaDaSessao() {
        String titulo = (String) comboFilmes.getSelectedItem();
        String sessaoStr = (String) comboSessoes.getSelectedItem();
        if (titulo == null || sessaoStr == null) return;

        sessaoSelecionada = DadosApp.getInstance().getListaSessoes().getSessoes().stream()
                .filter(s -> s.getFilme().getTitulo().equals(titulo))
                .filter(s -> sessaoStr.contains(s.getSala().getNome()))
                .findFirst()
                .orElse(null);

        if (sessaoSelecionada == null) return;

        Sala sala = sessaoSelecionada.getSala();
        int filas = sala.getNumFilas();
        int lugaresPorFila = sala.getLugaresPorFila();

        painelLugares.removeAll();
        painelLugares.setLayout(new GridLayout(filas, lugaresPorFila, 5, 5));
        lugaresBtns = new JButton[filas][lugaresPorFila];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < lugaresPorFila; j++) {
                JButton btn = new JButton((i + 1) + "-" + (j + 1));
                btn.setBackground(Color.GREEN);
                int finalI = i;
                int finalJ = j;
                btn.addActionListener(e -> selecionarLugar(finalI, finalJ));
                lugaresBtns[i][j] = btn;
                painelLugares.add(btn);
            }
        }

        painelLugares.revalidate();
        painelLugares.repaint();
    }

    private void selecionarLugar(int fila, int lugar) {
        if (filaSelecionada != -1 && lugarSelecionado != -1) {
            lugaresBtns[filaSelecionada][lugarSelecionado].setBackground(Color.GREEN);
        }
        filaSelecionada = fila;
        lugarSelecionado = lugar;
        lugaresBtns[fila][lugar].setBackground(Color.YELLOW);
    }

    private void confirmarLugar() {
        if (filaSelecionada == -1 || lugarSelecionado == -1 || sessaoSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione um lugar primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aqui será associada a informação ao bilhete (por outro membro)

        JOptionPane.showMessageDialog(this, "Bilhete configurado com sucesso!");
        dispose();
    }
}
