package com.lipe_kleiz.delivery_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lipe_kleiz.delivery_api.enums.StatusPedido;

import lombok.Data;

@Data
public class PedidoResponseDTO {

    private Long id;

    private Long clienteId;
    private String nomeCliente;

    private Long restauranteId;
    private String nomeRestaurante;

    private String enderecoEntrega;

    private BigDecimal subtotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;

    private StatusPedido status;

    private LocalDateTime dataPedido;
}