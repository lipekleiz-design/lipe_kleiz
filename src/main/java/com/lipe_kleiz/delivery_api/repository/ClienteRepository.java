package com.lipe_kleiz.delivery_api.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface JpaRepository do Spring Data JPA

import com.lipe_kleiz.delivery_api.model.Cliente; // Importa a classe Cliente do pacote model


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // A interface ClienteRepository estende JpaRepository,
    //  o que significa que ela herda métodos para
    //  realizar operações de CRUD (Create, Read, Update, Delete)
    //  e outras operações de banco de dados para a entidade Cliente.
    // O primeiro parâmetro <Cliente> indica o tipo da entidade
    //  que esta interface irá gerenciar,
    //  e o segundo parâmetro <Long> indica o tipo do identificador (ID) da entidade Cliente.
}
