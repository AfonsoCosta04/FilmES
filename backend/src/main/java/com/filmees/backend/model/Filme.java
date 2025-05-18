package com.filmees.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFilme;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer ano;

    @Column(name = "duracao_horas", nullable = false)
    private Integer duracaoHoras;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    private Double imdb; // Opcional

    @Column(name = "rotten_tomatoes")
    private String rottenTomatoes; // Opcional

    @Column(nullable = false)
    private String genero1;

    private String genero2; // Opcional
    private String genero3; // Opcional

    @Column(nullable = false)
    private String ator1;

    private String ator2; // Opcional
    private String ator3; // Opcional

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sinopse;

    @Column(nullable = false)
    private Double preco;

    private String foto; // Opcional

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(name = "idade_recomendada")
    private Integer idadeRecomendada; // Opcional

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CarrinhoFilme> carrinhos;

    @ManyToMany(mappedBy = "filmes")
    @JsonIgnore
    private List<Aluguer> alugueres;

    // Getters e Setters

    public Integer getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Integer idFilme) {
        this.idFilme = idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(Integer duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Double getImdb() {
        return imdb;
    }

    public void setImdb(Double imdb) {
        this.imdb = imdb;
    }

    public String getRottenTomatoes() {
        return rottenTomatoes;
    }

    public void setRottenTomatoes(String rottenTomatoes) {
        this.rottenTomatoes = rottenTomatoes;
    }

    public String getGenero1() {
        return genero1;
    }

    public void setGenero1(String genero1) {
        this.genero1 = genero1;
    }

    public String getGenero2() {
        return genero2;
    }

    public void setGenero2(String genero2) {
        this.genero2 = genero2;
    }

    public String getGenero3() {
        return genero3;
    }

    public void setGenero3(String genero3) {
        this.genero3 = genero3;
    }

    public String getAtor1() {
        return ator1;
    }

    public void setAtor1(String ator1) {
        this.ator1 = ator1;
    }

    public String getAtor2() {
        return ator2;
    }

    public void setAtor2(String ator2) {
        this.ator2 = ator2;
    }

    public String getAtor3() {
        return ator3;
    }

    public void setAtor3(String ator3) {
        this.ator3 = ator3;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Integer getIdadeRecomendada() {
        return idadeRecomendada;
    }

    public void setIdadeRecomendada(Integer idadeRecomendada) {
        this.idadeRecomendada = idadeRecomendada;
    }

    public List<Aluguer> getAlugueres() { return alugueres; }

    public void setAlugueres(List<Aluguer> alugueres) { this.alugueres = alugueres; }

    @Transient
    public int getDuracaoTotalMinutos() { return (duracaoHoras * 60) + duracaoMinutos; }
}
