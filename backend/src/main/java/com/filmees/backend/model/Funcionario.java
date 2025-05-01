package com.filmees.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;

    @Column(name = "nome_funcionario", nullable = false)
    private String nomeFuncionario;

    @Column(name = "email_funcionario", nullable = false, unique = true)
    private String emailFuncionario;

    @Column(nullable = true, unique = true)
    private String telefone;

    @Column(name = "password_funcionario", nullable = false)
    private String passwordFuncionario;

    @Column(name = "perm_leitura")
    private Boolean permLeitura = false;

    @Column(name = "perm_criacao")
    private Boolean permCriacao = false;

    @Column(name = "perm_edicao")
    private Boolean permEdicao = false;

    // Getters e Setters

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmailFuncionario() {
        return emailFuncionario;
    }

    public void setEmailFuncionario(String emailFuncionario) {
        this.emailFuncionario = emailFuncionario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPasswordFuncionario() {
        return passwordFuncionario;
    }

    public void setPasswordFuncionario(String passwordFuncionario) {
        this.passwordFuncionario = passwordFuncionario;
    }

    public Boolean getPermLeitura() {
        return permLeitura;
    }

    public void setPermLeitura(Boolean permLeitura) {
        this.permLeitura = permLeitura;
    }

    public Boolean getPermCriacao() {
        return permCriacao;
    }

    public void setPermCriacao(Boolean permCriacao) {
        this.permCriacao = permCriacao;
    }

    public Boolean getPermEdicao() {
        return permEdicao;
    }

    public void setPermEdicao(Boolean permEdicao) {
        this.permEdicao = permEdicao;
    }
}

