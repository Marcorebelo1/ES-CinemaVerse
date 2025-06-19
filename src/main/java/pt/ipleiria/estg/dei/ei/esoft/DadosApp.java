package pt.ipleiria.estg.dei.ei.esoft;

import pt.ipleiria.estg.dei.ei.esoft.classes.Carrinho;
import pt.ipleiria.estg.dei.ei.esoft.classes.Filme;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaFilmes;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaProdutos;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaSalas;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaSessoes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DadosApp {
    private static DadosApp instance = null;
    private ListaSalas listaSalas;
    private ListaProdutos listaProdutos;
    private Carrinho carrinho;
    private ListaFilmes listaFilmes;
    private List<Filme> filmesCatalogo;
    private ListaSessoes listaSessoes;

//    private List<Livro> livros = new ArrayList<>();

    private DadosApp() {
//        livros.add(new Livro("Ensaio Sobre a Cegueira", "José Saramago", "9789722113201"));
//        livros.add(new Livro("Os Maias", "Eça de Queirós", "9789723713660"));
//        livros.add(new Livro("Memorial do Convento", "José Saramago", "9789722113256"));
//        livros.add(new Livro("A Cidade e as Serras", "Eça de Queirós", "9789723715954"));
//        livros.add(new Livro("Livro do Desassossego", "Fernando Pessoa", "9789727086494"));
//        livros.add(new Livro("Amor de Perdição", "Camilo Castelo Branco", "9789897023476"));
//        livros.add(new Livro("A Costa dos Murmúrios", "Lídia Jorge", "9789722117001"));
//        livros.add(new Livro("A Sibila", "Agustina Bessa-Luís", "9789722115632"));
//        livros.add(new Livro("Jerusalém", "Gonçalo M. Tavares", "9789726081940"));
//        livros.add(new Livro("Nenhum Olhar", "José Luís Peixoto", "9789720048505"));

        listaSalas = new ListaSalas();
        listaProdutos = new ListaProdutos();
        carrinho = new Carrinho();
        listaFilmes = new ListaFilmes();
        listaSessoes = new ListaSessoes();

        filmesCatalogo = new ArrayList<>(List.of(
                //new Filme("Until Up", 90, "12+", "Animação", "Original", false, "Pixar", 0, null),
                //new Filme("John Sick", 110, "16+", "Ação", "Dublada", true, "Universal", 0, null),
                new Filme("Avengers: Lamegame", 130, "12+", "Ficção", "Original", true, "Marvel", 0, null),
                new Filme("My Little Poney: Inside Bowser's Castle", 95, "3+", "Infantil", "Dublada", false, "Nintendo", 0, null),
                new Filme("Hairy Plotter", 120, "10+", "Fantasia", "Original", false, "WB", 0, null),
                new Filme("Fast & Curious 5", 105, "14+", "Ação", "Original", true, "Paramount", 0, null)
        ));

        listaFilmes.addToEndOfList(new Filme("Until Up", 90, "12+", "Animação", "Original", false, "Pixar", 14, LocalDate.now().minusDays(2)));
        listaFilmes.addToEndOfList(new Filme("John Sick", 110, "16+", "Ação", "Dublada", true, "Universal", 7, LocalDate.now().minusDays(1)));

        //carregarDados();
    }

    public static DadosApp getInstance() {
        if (instance == null) {
            instance = new DadosApp();
            carregarDados();
        }
        return instance;
    }

    private static void carregarDados() {
    }

    private static void gravarDados() {
        //Guardar Ficheiro Texto

    }

//    public List<Livro> getLivros() {
//        return livros;
//    }


    public ListaSalas getListaSalas() {
        if (listaSalas == null) {
            listaSalas = new ListaSalas();
        }
        return listaSalas;
    }

    public ListaProdutos getListaProdutos() {
        return listaProdutos;
    }

    public Carrinho getCarrinho() {
        if (carrinho == null) {
            carrinho = new Carrinho();
        }
        return carrinho;
    }

    public ListaFilmes getListaFilmes() {
        if (listaFilmes == null) {
            listaFilmes = new ListaFilmes();
        }
        return listaFilmes;
    }

    public List<Filme> getFilmesCatalogo() {
        return new ArrayList<>(filmesCatalogo);
    }

    public Filme getFilmeDoCatalogo(String titulo) {
        return filmesCatalogo.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public ListaSessoes getListaSessoes() {
        if (listaSessoes == null) {
            listaSessoes = new ListaSessoes();
        }
        return listaSessoes;
    }
}
