package com.lipe_kleiz.delivery_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemPedidoDTO {

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Max(value = 10, message = "Quantidade máxima é 10")
    private Integer quantidade;
}