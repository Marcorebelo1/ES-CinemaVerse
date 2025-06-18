package pt.ipleiria.estg.dei.ei.esoft.views.paineis;

import javax.swing.*;
import java.awt.*;


/*
É um simples JPanel com uma mensagem de boas-vindas.
Mostra instruções, objetivos e nome da aplicação.
É a vista inicial quando se inicia o programa.
*/

public class PainelHome extends JPanel {
    public PainelHome() {
        setLayout(new BorderLayout());
        JTextArea texto = new JTextArea(
                "Bem-vindo ao Sistema de Gestão Cineverse\n\n" +
                        "O Cineverse é a plataforma ideal para gerir todos os aspetos do seu espaço de cinema de forma simples, intuitiva e eficiente.\n\n" +
                        "Aqui poderá configurar salas, agendar sessões, vender bilhetes, gerir o bar e consultar estatísticas detalhadas sobre o desempenho do seu negócio.\n\n" +
                        "Navegue pelo menu superior para aceder às diferentes funcionalidades.\n\n" +
                        "A sua experiência e a satisfação dos seus clientes começam aqui!"
        );
        texto.setEditable(false);
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setFont(new Font("Arial", Font.PLAIN, 16));
        texto.setMargin(new Insets(20, 20, 20, 20));
        add(texto, BorderLayout.CENTER);
    }
}
