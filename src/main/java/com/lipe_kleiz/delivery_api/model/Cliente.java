package com.lipe_kleiz.delivery_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // A anotação @Entity indica que esta classe é uma entidade JPA, ou seja, ela representa uma tabela no banco de dados.
public class Cliente {
    //A classe Cliente é uma entidade que representa um cliente em um sistema de delivery.
    @Id
    //A anotação @Id indica que o campo id é a chave primária da entidade Cliente.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //A anotação @GeneratedValue com a estratégia GenerationType.IDENTITY indica que o valor do campo id será gerado automaticamente pelo banco de dados, geralmente usando uma coluna auto-incremento.

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    public Cliente() {
    }

    //getters e setters para os campos da classe Cliente
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
