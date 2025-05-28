package com.filmees.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Aluguer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAluguer;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    private LocalDate dataLevantamento;
    private LocalDate dataDevolucao;
    private String estado;

    @ManyToMany
    @JoinTable(
            name = "aluguer_filme",
            joinColumns = @JoinColumn(name = "id_aluguer"),
            inverseJoinColumns = @JoinColumn(name = "id_filme")
    )
    private List<Filme> filmes;

    public Integer getIdAluguer() {
        return idAluguer;
    }

    public void setIdAluguer(Integer idAluguer) {
        this.idAluguer = idAluguer;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataLevantamento() {
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDate dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }
}
