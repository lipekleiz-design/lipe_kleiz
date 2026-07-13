package com.lipe_kleiz.delivery_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.config.SwaggerConfig;
import com.lipe_kleiz.delivery_api.dto.ClienteDTO;
import com.lipe_kleiz.delivery_api.dto.ClienteResponseDTO;
import com.lipe_kleiz.delivery_api.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(
    name = "Clientes",
    description = "Endpoints para gerenciamento de clientes"
)
@RestController
@RequestMapping("/api/clientes")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
        summary = "Cadastrar cliente",
        description = "Realiza o cadastro de um novo cliente no sistema"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(
            @Valid @RequestBody ClienteDTO dto) {

        ClienteResponseDTO cliente = clienteService.cadastrarCliente(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cliente);
    }

    @Operation(
        summary = "Buscar cliente por ID",
        description = "Retorna os dados de um cliente específico"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        ClienteResponseDTO cliente =
                clienteService.buscarClientePorId(id);

        return ResponseEntity.ok(cliente);
    }

    @Operation(
        summary = "Listar clientes ativos",
        description = "Retorna todos os clientes ativos cadastrados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {

        List<ClienteResponseDTO> clientes =
                clienteService.listarClientesAtivos();

        return ResponseEntity.ok(clientes);
    }

    @Operation(
        summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {

        ClienteResponseDTO cliente =
                clienteService.atualizarCliente(id, dto);

        return ResponseEntity.ok(cliente);
    }

    @Operation(
        summary = "Ativar ou desativar cliente",
        description = "Alterna o status do cliente entre ativo e inativo"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResponseDTO> ativarDesativarCliente(
            @PathVariable Long id) {

        ClienteResponseDTO cliente =
                clienteService.ativarDesativarCliente(id);

        return ResponseEntity.ok(cliente);
    }

    @Operation(
        summary = "Buscar cliente por e-mail",
        description = "Retorna um cliente a partir do e-mail informado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(
            @PathVariable String email) {

        ClienteResponseDTO cliente =
                clienteService.buscarClientePorEmail(email);

        return ResponseEntity.ok(cliente);
    }
}