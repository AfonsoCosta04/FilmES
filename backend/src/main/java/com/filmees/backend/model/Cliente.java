package com.filmees.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

    @Column(name = "email_cliente", nullable = false, unique = true)
    private String emailCliente;

    @Column(name = "data_de_nasc_cliente", nullable = false)
    private LocalDate dataDeNascCliente;

    @Column(name = "numero_de_telefone", nullable = false, unique = true)
    private String numeroDeTelefone;

    @Column(name = "password_cliente", nullable = false)
    private String passwordCliente;

    @Column(nullable = true)
    private String contribuinte;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aluguer> alugueres;

    // Getters e Setters

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public LocalDate getDataDeNascCliente() {
        return dataDeNascCliente;
    }

    public void setDataDeNascCliente(LocalDate dataDeNascCliente) {
        this.dataDeNascCliente = dataDeNascCliente;
    }

    public String getNumeroDeTelefone() {
        return numeroDeTelefone;
    }

    public void setNumeroDeTelefone(String numeroDeTelefone) {
        this.numeroDeTelefone = numeroDeTelefone;
    }

    public String getPasswordCliente() {
        return passwordCliente;
    }

    public void setPasswordCliente(String passwordCliente) {
        this.passwordCliente = passwordCliente;
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }
}

