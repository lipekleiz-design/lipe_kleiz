package com.lipe_kleiz.delivery_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lipe_kleiz.delivery_api.dto.ClienteDTO;
import com.lipe_kleiz.delivery_api.dto.ClienteResponseDTO;
import com.lipe_kleiz.delivery_api.entity.Cliente;
import com.lipe_kleiz.delivery_api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDTO dto;

    @BeforeEach
    void setup() {

        cliente = new Cliente();

        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
        cliente.setEndereco("Rua A");
        cliente.setAtivo(true);

        dto = new ClienteDTO();

        dto.setNome("João Silva");
        dto.setEmail("joao@email.com");
        dto.setTelefone("11999999999");
        dto.setEndereco("Rua A");
    }

    @Test
    @DisplayName("Deve cadastrar cliente")
    void deveCadastrarCliente() {

        when(clienteRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(clienteRepository.save(any(Cliente.class)))
                .thenReturn(cliente);

        ClienteResponseDTO response =
                clienteService.cadastrarCliente(dto);

        assertNotNull(response);
        assertEquals("João Silva", response.getNome());
        assertEquals("joao@email.com", response.getEmail());

        verify(clienteRepository).existsByEmail(dto.getEmail());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Não deve cadastrar cliente com email duplicado")
    void naoDeveCadastrarClienteComEmailDuplicado() {

        when(clienteRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> clienteService.cadastrarCliente(dto));

        assertEquals(
                "Email já cadastrado: " + dto.getEmail(),
                exception.getMessage());

        verify(clienteRepository, never())
                .save(any(Cliente.class));
    }

}