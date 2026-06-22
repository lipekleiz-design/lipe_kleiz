package com.lipe_kleiz.delivery_api.dto;

import com.lipe_kleiz.delivery_api.enums.StatusPedido;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusPedidoDTO {

    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;
}