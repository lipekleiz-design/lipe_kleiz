package com.lipe_kleiz.delivery_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.dto.ClienteDTO;
import com.lipe_kleiz.delivery_api.dto.ClienteResponseDTO;
import com.lipe_kleiz.delivery_api.model.Cliente;
import com.lipe_kleiz.delivery_api.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponseDTO cadastrarCliente(ClienteDTO dto) {

        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(
                "Email já cadastrado: " + dto.getEmail());
        }

        Cliente cliente = new Cliente();

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(dto.getEndereco());
        cliente.setAtivo(true);

        Cliente clienteSalvo =
                clienteRepository.save(cliente);

        return converterParaDTO(clienteSalvo);
    }

    @Override
    public ClienteResponseDTO buscarClientePorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente não encontrado com ID: " + id));

        return converterParaDTO(cliente);
    }

    @Override
    public ClienteResponseDTO buscarClientePorEmail(String email) {

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente não encontrado com email: " + email));

        return converterParaDTO(cliente);
    }

    @Override
    public ClienteResponseDTO atualizarCliente(
            Long id,
            ClienteDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente não encontrado com ID: " + id));

        if (!cliente.getEmail().equals(dto.getEmail())
                && clienteRepository.existsByEmail(dto.getEmail())) {

            throw new RuntimeException(
                    "Email já cadastrado: " + dto.getEmail());
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(dto.getEndereco());

        Cliente clienteAtualizado =
                clienteRepository.save(cliente);

        return converterParaDTO(clienteAtualizado);
    }

    @Override
    public ClienteResponseDTO ativarDesativarCliente(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente não encontrado com ID: " + id));

        cliente.setAtivo(!cliente.isAtivo());

        Cliente clienteAtualizado =
                clienteRepository.save(cliente);

        return converterParaDTO(clienteAtualizado);
    }

    @Override
    public List<ClienteResponseDTO> listarClientesAtivos() {

        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private ClienteResponseDTO converterParaDTO(
            Cliente cliente) {

        ClienteResponseDTO dto =
                new ClienteResponseDTO();

        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setEndereco(cliente.getEndereco());
        dto.setAtivo(cliente.isAtivo());

        return dto;
    }
}