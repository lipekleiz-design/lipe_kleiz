package com.lipe_kleiz.delivery_api.model;

import java.math.BigDecimal;

import com.lipe_kleiz.delivery_api.enums.CategoriaProduto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private CategoriaProduto categoria;

    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;
}