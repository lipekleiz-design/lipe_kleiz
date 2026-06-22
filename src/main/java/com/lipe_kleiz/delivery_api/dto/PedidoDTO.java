package com.lipe_kleiz.delivery_api.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200,
          message = "Endereço deve ter no máximo 200 caracteres")
    private String enderecoEntrega;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;
}