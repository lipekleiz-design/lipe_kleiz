package com.lipe_kleiz.delivery_api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Wrapper padrão para respostas da API")
public class ApiResponseWrapper<T> {


@Schema(
    description = "Indica se a operação foi bem-sucedida",
    example = "true"
)
private boolean success;

@Schema(description = "Dados da resposta")
private T data;

@Schema(
    description = "Mensagem da operação",
    example = "Operação realizada com sucesso"
)
private String message;

@Schema(
    description = "Data e hora da resposta"
)
private LocalDateTime timestamp = LocalDateTime.now();

public ApiResponseWrapper(
        boolean success,
        T data,
        String message) {

    this.success = success;
    this.data = data;
    this.message = message;
    this.timestamp = LocalDateTime.now();
}

public static <T> ApiResponseWrapper<T> success(
        T data,
        String message) {

    return new ApiResponseWrapper<>(
            true,
            data,
            message);
}

public static <T> ApiResponseWrapper<T> error(
        String message) {

    return new ApiResponseWrapper<>(
            false,
            null,
            message);
}

}
