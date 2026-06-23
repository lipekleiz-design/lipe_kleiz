package com.lipe_kleiz.delivery_api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Dados de retorno de um restaurante")
public class RestauranteResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Pizza Express")
    private String nome;

    @Schema(example = "Italiana")
    private String categoria;

    @Schema(example = "Rua das Flores, 123")
    private String endereco;

    @Schema(example = "21999999999")
    private String telefone;

    @Schema(example = "5.50")
    private BigDecimal taxaEntrega;

    @Schema(example = "true")
    private Boolean ativo;
}