package com.lipe_kleiz.delivery_api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.model.Cliente;
import com.lipe_kleiz.delivery_api.service.ClienteService;

@RestController //essa anotação @RestController indica que esta classe é um controlador REST do Spring, o que a torna um componente gerenciado pelo Spring e permite que ela seja usada para lidar com requisições HTTP e retornar respostas em formato JSON ou XML.
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes/total") //quando alguem acessar a rota /clientes/total, o método totalClientes() será chamado e retornará o número total de clientes cadastrados no sistema.
    public long totalClientes() {
        return clienteService.totalClientes();
    }

    @PostMapping("/clientes") //quando alguem fizer uma requisição POST para a rota /clientes, o método salvarCliente() será chamado e retornará o cliente salvo.
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }
}
