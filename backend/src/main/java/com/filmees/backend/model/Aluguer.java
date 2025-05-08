package com.filmees.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Aluguer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAluguer;

    @ManyToOne
    @JoinColumn(name = "id_filme", nullable = false)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    private LocalDate dataLevantamento;
    private LocalDate dataDevolucao;
    private String estado;

    public Integer getIdAluguer() {
        return idAluguer;
    }

    public void setIdAluguer(Integer idAluguer) {
        this.idAluguer = idAluguer;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
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
}
