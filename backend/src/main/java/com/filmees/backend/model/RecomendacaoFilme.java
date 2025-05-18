package com.filmees.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recomendacao_filme")
public class RecomendacaoFilme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_recomendacao", nullable = false)
    private Recomendacao recomendacao;

    @ManyToOne
    @JoinColumn(name = "id_filme", nullable = false)
    private Filme filme;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Recomendacao getRecomendacao() { return recomendacao; }

    public void setRecomendacao(Recomendacao recomendacao) { this.recomendacao = recomendacao; }

    public Filme getFilme() { return filme; }

    public void setFilme(Filme filme) { this.filme = filme; }
}

