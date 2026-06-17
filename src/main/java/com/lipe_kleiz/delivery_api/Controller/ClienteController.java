package com.lipe_kleiz.delivery_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.model.Cliente;
import com.lipe_kleiz.delivery_api.service.ClienteService;

@RestController
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/teste")
    public String teste() {
        return "API funcionando!";
    }

    @GetMapping("/clientes/total")
    public long totalClientes() {
        return clienteService.totalClientes();
    }

    @PostMapping("/clientes")
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }

  
}