package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import javax.swing.*;
import java.awt.*;


/*
Mostra instruções, objetivos e nome da aplicação.
É a vista inicial quando se inicia o programa.
*/

public class PainelHome extends JPanel {
    public PainelHome() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Painel de conteúdo principal (esquerda + direita)
        JPanel painelConteudo = new JPanel(new BorderLayout(30, 0)); // espaço entre texto e imagem
        painelConteudo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // contorno como no mockup
        painelConteudo.setBackground(Color.WHITE);
        painelConteudo.setPreferredSize(new Dimension(700, 300));
        painelConteudo.setMaximumSize(new Dimension(700, 300));

        // Texto à esquerda
        JTextArea texto = new JTextArea(
                "Bem-vindo ao Sistema de Gestão Cineverse\n\n" +
                        "    O Cineverse é a plataforma ideal para gerir todos os aspetos do seu espaço de cinema de forma simples, intuitiva e eficiente.\n\n" +
                        "    Aqui poderá configurar salas, agendar sessões, vender bilhetes, gerir o bar e consultar estatísticas detalhadas sobre o desempenho do seu negócio.\n" +
                        "Navegue pelo menu superior para aceder às diferentes funcionalidades.\n\n" +
                        "    A sua experiência e a satisfação dos seus clientes começam aqui!"
        );
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        texto.setBackground(Color.WHITE);
        texto.setMargin(new Insets(20, 20, 20, 20));
        texto.setBorder(null);

        // Logo à direita (pode ser substituído por uma imagem real se quiseres)
        JLabel logo = new JLabel("🎬 CINEVERSE", SwingConstants.CENTER);
        logo.setFont(new Font("SansSerif", Font.BOLD, 18));
        logo.setOpaque(true);
        logo.setBackground(new Color(240, 240, 240));
        logo.setPreferredSize(new Dimension(200, 200));
        logo.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Adicionar ao painel
        painelConteudo.add(texto, BorderLayout.CENTER);
        painelConteudo.add(logo, BorderLayout.EAST);

        // Centralizar no painel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(painelConteudo, gbc);
    }
}
