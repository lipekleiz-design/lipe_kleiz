package com.lipe_kleiz.delivery_api.service;

import java.util.List;

import com.lipe_kleiz.delivery_api.dto.ClienteDTO;
import com.lipe_kleiz.delivery_api.dto.ClienteResponseDTO;

public interface ClienteService { // Interface para o serviço de Cliente

    ClienteResponseDTO cadastrarCliente(ClienteDTO dto);

    ClienteResponseDTO buscarClientePorId(Long id);

    ClienteResponseDTO buscarClientePorEmail(String email);

    ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto);

    ClienteResponseDTO ativarDesativarCliente(Long id);

    List<ClienteResponseDTO> listarClientesAtivos();
}
