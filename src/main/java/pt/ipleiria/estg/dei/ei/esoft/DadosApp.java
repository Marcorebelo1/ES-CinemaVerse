package pt.ipleiria.estg.dei.ei.esoft;

import pt.ipleiria.estg.dei.ei.esoft.classes.Carrinho;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaProdutos;
import pt.ipleiria.estg.dei.ei.esoft.views.listas.ListaSalas;

public class DadosApp {
    private static DadosApp instance = null;
    private ListaSalas listaSalas;
    private ListaProdutos listaProdutos;
    private Carrinho carrinho;

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


}
