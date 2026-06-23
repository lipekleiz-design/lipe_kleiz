package com.lipe_kleiz.delivery_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.dto.ApiResponseWrapper;
import com.lipe_kleiz.delivery_api.dto.PagedResponseWrapper;
import com.lipe_kleiz.delivery_api.dto.RestauranteDTO;
import com.lipe_kleiz.delivery_api.dto.RestauranteResponseDTO;
import com.lipe_kleiz.delivery_api.service.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurantes")
@CrossOrigin(origins = "*")
@Tag(
name = "Restaurantes",
description = "Operações relacionadas aos restaurantes"
)
public class RestauranteController {

@Autowired
private RestauranteService restauranteService;

@PostMapping
@Operation(
    summary = "Cadastrar restaurante",
    description = "Cria um novo restaurante no sistema"
)
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
})
public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>>
        cadastrar(
                @Valid @RequestBody RestauranteDTO dto) {

    RestauranteResponseDTO restaurante =
            restauranteService.cadastrarRestaurante(dto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponseWrapper.success(
                    restaurante,
                    "Restaurante criado com sucesso"));
}

@GetMapping
@Operation(
    summary = "Listar restaurantes",
    description = "Lista restaurantes ativos com paginação"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
})
public ResponseEntity<PagedResponseWrapper<RestauranteResponseDTO>>
        listar(

                @Parameter(
                    description = "Categoria do restaurante"
                )
                @RequestParam(required = false)
                String categoria,

                @Parameter(
                    description = "Status ativo"
                )
                @RequestParam(required = false)
                Boolean ativo,

                Pageable pageable) {

    Page<RestauranteResponseDTO> restaurantes =
            restauranteService.listarRestaurantes(
                    categoria,
                    ativo,
                    pageable);

    return ResponseEntity.ok(
            new PagedResponseWrapper<>(
                    restaurantes));
}

@GetMapping("/{id}")
@Operation(
    summary = "Buscar restaurante por ID",
    description = "Recupera um restaurante específico"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
})
public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>>
        buscarPorId(

                @Parameter(
                    description = "ID do restaurante",
                    example = "1"
                )
                @PathVariable Long id) {

    RestauranteResponseDTO restaurante =
            restauranteService.buscarRestaurantePorId(id);

    return ResponseEntity.ok(
            ApiResponseWrapper.success(
                    restaurante,
                    "Restaurante encontrado"));
}

@PutMapping("/{id}")
@Operation(
    summary = "Atualizar restaurante",
    description = "Atualiza os dados de um restaurante"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
})
public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>>
        atualizar(

                @PathVariable Long id,

                @Valid @RequestBody RestauranteDTO dto) {

    RestauranteResponseDTO restaurante =
            restauranteService.atualizarRestaurante(
                    id,
                    dto);

    return ResponseEntity.ok(
            ApiResponseWrapper.success(
                    restaurante,
                    "Restaurante atualizado com sucesso"));
}

@PatchMapping("/{id}/status")
@Operation(
    summary = "Ativar/Desativar restaurante",
    description = "Alterna o status ativo do restaurante"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Status alterado"),
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
})
public ResponseEntity<ApiResponseWrapper<RestauranteResponseDTO>>
        alterarStatus(

                @PathVariable Long id) {

    RestauranteResponseDTO restaurante =
            restauranteService.alterarStatusRestaurante(id);

    return ResponseEntity.ok(
            ApiResponseWrapper.success(
                    restaurante,
                    "Status alterado com sucesso"));
}

@GetMapping("/categoria/{categoria}")
@Operation(
    summary = "Buscar por categoria",
    description = "Lista restaurantes de uma categoria específica"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
})
public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>>
        buscarPorCategoria(

                @PathVariable String categoria) {

    List<RestauranteResponseDTO> restaurantes =
            restauranteService.buscarPorCategoria(categoria);

    return ResponseEntity.ok(
            ApiResponseWrapper.success(
                    restaurantes,
                    "Restaurantes encontrados"));
}

@GetMapping("/ativos/total")
@Operation(
    summary = "Total de restaurantes ativos",
    description = "Retorna a quantidade de restaurantes ativos"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
})
public ResponseEntity<ApiResponseWrapper<Long>>
        totalRestaurantesAtivos() {

    return ResponseEntity.ok(
            ApiResponseWrapper.success(
                    restauranteService.totalRestaurantesAtivos(),
                    "Total recuperado com sucesso"));
}

}
