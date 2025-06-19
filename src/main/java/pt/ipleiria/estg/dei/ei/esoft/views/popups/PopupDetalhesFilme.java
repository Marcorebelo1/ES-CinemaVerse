package pt.ipleiria.estg.dei.ei.esoft.views.popups;

import pt.ipleiria.estg.dei.ei.esoft.DadosApp;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;
import pt.ipleiria.estg.dei.ei.esoft.views.paineis.PainelFilmes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
/**
 * Mostra os detalhes de um filme alugado.
 */
public class PopupDetalhesFilme extends JDialog {

    public PopupDetalhesFilme(JFrame parentFrame, Filme filme) {
        super(parentFrame, "Características - Filme Selecionado", true);
        setLayout(new GridLayout(8, 2, 10, 10));
        setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Título:"));
        add(new JLabel(filme.getTitulo()));

        add(new JLabel("Categoria:"));
        add(new JLabel(filme.getCategoria()));

        add(new JLabel("Duração:"));
        add(new JLabel(filme.getDuracao() + " min"));

        add(new JLabel("Idade recomendada:"));
        add(new JLabel(filme.getClassificacaoEtaria()));

        add(new JLabel("3D:"));
        add(new JLabel(filme.isIs3D() ? "Y" : "N"));

        add(new JLabel("Idioma:"));
        add(new JLabel(filme.getVersao()));

        add(new JLabel("Duração Licença:"));
        int dias = filme.getDuracaoLicencaDias();
        String duracaoTexto = dias == 7 ? "1 semana" : dias == 30 ? "1 mês" : (dias % 7 == 0 ? (dias / 7) + " semanas" : dias + " dias");
        add(new JLabel(duracaoTexto));

        add(new JLabel("Fornecedor:"));
        add(new JLabel(filme.getFornecedor()));

        setSize(400, 320);
        setLocationRelativeTo(parentFrame);
    }
}