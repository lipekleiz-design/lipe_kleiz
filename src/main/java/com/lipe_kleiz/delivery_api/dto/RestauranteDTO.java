package com.lipe_kleiz.delivery_api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Dados para cadastro e atualização de restaurantes")
public class RestauranteDTO {

    @Schema(
        description = "Nome do restaurante",
        example = "Pizza Express"
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(
        description = "Categoria do restaurante",
        example = "Italiana"
    )
    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    @Schema(
        description = "Endereço do restaurante",
        example = "Rua das Flores, 123"
    )
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Schema(
        description = "Telefone para contato",
        example = "21999999999"
    )
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
        regexp = "\\d{10,11}",
        message = "Telefone deve conter 10 ou 11 dígitos"
    )
    private String telefone;

    @Schema(
        description = "Taxa de entrega",
        example = "5.50"
    )
    @NotNull(message = "Taxa de entrega é obrigatória")
    @DecimalMin(
        value = "0.0",
        inclusive = true,
        message = "Taxa de entrega não pode ser negativa"
    )
    private BigDecimal taxaEntrega;
}