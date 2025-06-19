package pt.ipleiria.estg.dei.ei.esoft.classes;


import java.time.LocalDate;

public class Filme {
    private String titulo;
    private int duracao; // em minutos
    private String classificacaoEtaria;
    private String categoria;
    private String versao; // "Original" ou "Dublada"
    private boolean is3D;
    private String fornecedor;
    private int duracaoLicencaDias; // Duração da licença em dias
    private LocalDate dataAluguer;

    public Filme(String titulo, int duracao, String classificacaoEtaria, String categoria,
                 String versao, boolean is3D, String fornecedor,
                 int duracaoLicencaDias, LocalDate dataAluguer) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.classificacaoEtaria = classificacaoEtaria;
        this.categoria = categoria;
        this.versao = versao;
        this.is3D = is3D;
        this.fornecedor = fornecedor;
        this.duracaoLicencaDias = duracaoLicencaDias;
        this.dataAluguer = dataAluguer;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getVersao() {
        return versao;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public int getDuracaoLicencaDias() {
        return duracaoLicencaDias;
    }

    public LocalDate getDataAluguer() {
        return dataAluguer;
    }

    /**
     * Verifica se o filme está com licença ativa.
     */
    public boolean isAtivo() {
        return dataAluguer.plusDays(duracaoLicencaDias).isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return titulo;
    }
}
