package com.lipe_kleiz.delivery_api.service;

import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.model.Cliente;
import com.lipe_kleiz.delivery_api.repository.ClienteRepository;

@Service // A anotação @Service indica que esta classe é um serviço do Spring, o que a torna um componente gerenciado pelo Spring e permite que ela seja injetada em outras partes da aplicação.
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public long totalClientes() {
        return clienteRepository.count();
    }
    
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
