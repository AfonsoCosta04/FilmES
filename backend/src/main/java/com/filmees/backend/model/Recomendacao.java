package com.filmees.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recomendacao")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @OneToMany(mappedBy = "recomendacao", cascade = CascadeType.ALL)
    private List<RecomendacaoFilme> recomendacoesFilmes = new ArrayList<>();

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Funcionario getFuncionario() { return funcionario; }

    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public List<RecomendacaoFilme> getRecomendacoesFilmes() { return recomendacoesFilmes; }

    public void setRecomendacoesFilmes(List<RecomendacaoFilme> recomendacoesFilmes) { this.recomendacoesFilmes = recomendacoesFilmes; }
}