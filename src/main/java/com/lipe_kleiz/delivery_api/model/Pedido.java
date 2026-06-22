package com.lipe_kleiz.delivery_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lipe_kleiz.delivery_api.enums.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;

    private String enderecoEntrega;

    private BigDecimal taxaEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(
        mappedBy = "pedido",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    @PrePersist
    public void prePersist() {

        if (dataPedido == null) {
            dataPedido = LocalDateTime.now();
        }

        if (status == null) {
            status = StatusPedido.PENDENTE;
        }
    }

    @Transient
    public BigDecimal getSubtotal() {

        if (itens == null || itens.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transient
    public BigDecimal getValorTotal() {

        BigDecimal subtotal = getSubtotal();

        if (taxaEntrega == null) {
            return subtotal;
        }

        return subtotal.add(taxaEntrega);
    }
}