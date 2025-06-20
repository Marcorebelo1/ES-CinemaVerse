package pt.ipleiria.estg.dei.ei.esoft.classes;

import java.io.Serializable;

public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorIds = 1;

    private final int id;
    private String nome;
    private int numFilas;
    private int lugaresPorFila;
    private boolean dolbyAtmos;
    private boolean acessibilidade;
    private boolean arCondicionado;
    private boolean ativa;




    public Sala(String nome, int numFilas, int lugaresPorFila, boolean dolbyAtmos,
                boolean acessibilidade, boolean arCondicionado) {
        this.id = contadorIds++;
        this.nome = nome;
        this.numFilas = numFilas;
        this.lugaresPorFila = lugaresPorFila;
        this.dolbyAtmos = dolbyAtmos;
        this.acessibilidade = acessibilidade;
        this.arCondicionado = arCondicionado;
        this.ativa = true;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public int getLugaresPorFila() {
        return lugaresPorFila;
    }

    public boolean isDolbyAtmos() {
        return dolbyAtmos;
    }

    public boolean isAcessibilidade() {
        return acessibilidade;
    }

    public boolean isArCondicionado() {
        return arCondicionado;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    public void setLugaresPorFila(int lugaresPorFila) {
        this.lugaresPorFila = lugaresPorFila;
    }

    public void setDolbyAtmos(boolean dolbyAtmos) {
        this.dolbyAtmos = dolbyAtmos;
    }

    public void setAcessibilidade(boolean acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id + "- Sala " + nome;
    }


}
