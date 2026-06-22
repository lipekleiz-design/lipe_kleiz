package com.lipe_kleiz.delivery_api.controller;

import java.util.List;

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

import com.lipe_kleiz.delivery_api.dto.ClienteDTO;
import com.lipe_kleiz.delivery_api.dto.ClienteResponseDTO;
import com.lipe_kleiz.delivery_api.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(
            @Valid @RequestBody ClienteDTO dto) {

        ClienteResponseDTO cliente =
                clienteService.cadastrarCliente(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.buscarClientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {

        return ResponseEntity.ok(
                clienteService.listarClientesAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {

        return ResponseEntity.ok(
                clienteService.atualizarCliente(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ClienteResponseDTO> ativarDesativarCliente(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.ativarDesativarCliente(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteResponseDTO> buscarPorEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                clienteService.buscarClientePorEmail(email));
    }
}