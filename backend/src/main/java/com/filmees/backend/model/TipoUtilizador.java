package com.filmees.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_utilizador")
public class TipoUtilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipo;

    @Column(nullable = false)
    private String descricao;

    // Getters e Setters

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
