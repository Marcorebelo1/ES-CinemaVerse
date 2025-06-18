package pt.ipleiria.estg.dei.ei.esoft.views;

import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelSalas;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelHome;

import javax.swing.*;
import java.awt.*;


/*
A estrutura principal da janela da aplicação.

Contém:
A topbar com botões (Home, Salas, Sessões, etc.)
Um painel central controlado por CardLayout que alterna entre PainelHome, ListaSalas, etc.
Responsável por:
    Inicializar os painéis
    Gerir qual o painel que está visível
    Fazer a ligação entre os botões da topbar e os conteúdos diferentes
*/

public class BarraDeNavegacao extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel painelCentral = new JPanel(cardLayout);

    public BarraDeNavegacao() {
        setTitle("Cineverse");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Logo
        JLabel logoLabel = new JLabel("🎬 CineVerse");
        //logoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        logoLabel.setForeground(Color.WHITE);

        // Botões do centro
        JPanel botoesCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botoesCentro.setOpaque(false);
        String[] seccoes = {"Home", "Salas", "Sessões", "Filmes", "Stock", "Vender", "Estatísticas"};
        for (String nome : seccoes) {
            JButton btn = criarBotaoTopbar(nome);
            btn.addActionListener(e -> {
                switch (nome) {
                    case "Home" -> btnHomeActionPerformed();
                    case "Salas" -> btnSalasActionPerformed();
                }
            });
            botoesCentro.add(btn);
        }

        // Icons à direita
        JPanel iconesDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconesDireita.setOpaque(false);
        JLabel carrinho = new JLabel("\uD83D\uDED2"); // carrinho
        JLabel disquete = new JLabel("\uD83D\uDCBE"); // disquete
        carrinho.setFont(new Font("SansSerif", Font.PLAIN, 18));
        disquete.setFont(new Font("SansSerif", Font.PLAIN, 18));
        iconesDireita.add(carrinho);
        iconesDireita.add(disquete);

        // Painel da Topbar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(160, 160, 160)); // Cinzento claro
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topBar.add(logoLabel, BorderLayout.WEST);
        topBar.add(botoesCentro, BorderLayout.CENTER);
        topBar.add(iconesDireita, BorderLayout.EAST);

        // Paineis de Conteudo
        painelCentral.add(new PainelHome(), "Home");
        painelCentral.add(new PainelSalas(), "Salas");

        // Adicionar à janela
        add(topBar, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        setVisible(true);
    }

    // Botão Home do diagrama
    private void btnHomeActionPerformed() {
        mostrar("Home");
    }

    // Botão Salas do diagrama
    private void btnSalasActionPerformed() {
        mostrar("Salas");
    }

    // Metodo comum para alternar vistas
    private void mostrar(String nomePainel) {
        cardLayout.show(painelCentral, nomePainel);
    }

    private JButton criarBotaoTopbar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);                    // Remove borda ao focar
        btn.setContentAreaFilled(false);               // Remove preenchimento
        btn.setBorderPainted(false);                   // Remove borda
        btn.setForeground(Color.WHITE);                // Texto branco
        btn.setFont(new Font("SansSerif", Font.BOLD, 13)); // Fonte similar
        return btn;
    }
}
