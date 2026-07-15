package com.lipe_kleiz.delivery_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import com.lipe_kleiz.delivery_api.dto.ItemPedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoResponseDTO;
import com.lipe_kleiz.delivery_api.entity.Cliente;
import com.lipe_kleiz.delivery_api.entity.ItemPedido;
import com.lipe_kleiz.delivery_api.entity.Pedido;
import com.lipe_kleiz.delivery_api.entity.Produto;
import com.lipe_kleiz.delivery_api.entity.Restaurante;
import com.lipe_kleiz.delivery_api.enums.StatusPedido;
import com.lipe_kleiz.delivery_api.repository.ClienteRepository;
import com.lipe_kleiz.delivery_api.repository.PedidoRepository;
import com.lipe_kleiz.delivery_api.repository.ProdutoRepository;
import com.lipe_kleiz.delivery_api.repository.RestauranteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do PedidoService")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Cliente cliente;
    private Restaurante restaurante;
    private Produto produto;
    private Pedido pedido;
    private PedidoDTO pedidoDTO;
    private ItemPedidoDTO itemDTO;

    @BeforeEach
    void setup() {

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setAtivo(true);

        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Pizzaria Itália");
        restaurante.setAtivo(true);
        restaurante.setTaxaEntrega(new BigDecimal("8.50"));

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Pizza Calabresa");
        produto.setPreco(new BigDecimal("50.00"));
        produto.setDisponivel(true);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setEnderecoEntrega("Rua das Flores");
        pedido.setTaxaEntrega(new BigDecimal("8.50"));
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setDataPedido(LocalDateTime.now());

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPrecoUnitario(new BigDecimal("50.00"));

        pedido.getItens().add(item);

        itemDTO = new ItemPedidoDTO();
        itemDTO.setProdutoId(1L);
        itemDTO.setQuantidade(2);

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setRestauranteId(1L);
        pedidoDTO.setEnderecoEntrega("Rua das Flores");
        pedidoDTO.setItens(List.of(itemDTO));

    }

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void deveCriarPedidoComSucesso() {

        // Arrange

        when(clienteRepository.findById(1L))
                .thenReturn(Optional.of(cliente));

        when(restauranteRepository.findById(1L))
                .thenReturn(Optional.of(restaurante));

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenReturn(pedido);

        // Act

        PedidoResponseDTO response =
                pedidoService.criarPedido(pedidoDTO);

        // Assert

        assertNotNull(response);

        assertEquals(1L, response.getClienteId());
        assertEquals(1L, response.getRestauranteId());
        assertEquals(StatusPedido.PENDENTE, response.getStatus());

        verify(clienteRepository).findById(1L);
        verify(restauranteRepository).findById(1L);
        verify(produtoRepository).findById(1L);
        verify(pedidoRepository).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Deve buscar pedido por ID")
    void deveBuscarPedidoPorId() {

        // Arrange

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        // Act

        PedidoResponseDTO response =
                pedidoService.buscarPedidoPorId(1L);

        // Assert

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve calcular o total do pedido")
    void deveCalcularTotalPedido() {

        // Arrange

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        // Act

        BigDecimal total =
                pedidoService.calcularTotalPedido(
                        pedidoDTO.getItens());

        // Assert

        assertEquals(new BigDecimal("100.00"), total);

        verify(produtoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve cancelar pedido")
    void deveCancelarPedido() {

        // Arrange

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));

        // Act

        pedidoService.cancelarPedido(1L);

        // Assert

        assertEquals(StatusPedido.CANCELADO,
                pedido.getStatus());

        verify(pedidoRepository).save(pedido);
    }

@Test
@DisplayName("Não deve criar pedido quando cliente não existe")
void naoDeveCriarPedidoQuandoClienteNaoExiste() {

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.empty());

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals("Cliente não encontrado",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve criar pedido quando cliente está inativo")
void naoDeveCriarPedidoQuandoClienteInativo() {

    cliente.setAtivo(false);

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.of(cliente));

    when(restauranteRepository.findById(1L))
            .thenReturn(Optional.of(restaurante));

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals("Cliente inativo",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve criar pedido quando restaurante não existe")
void naoDeveCriarPedidoQuandoRestauranteNaoExiste() {

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.of(cliente));

    when(restauranteRepository.findById(1L))
            .thenReturn(Optional.empty());

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals("Restaurante não encontrado",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve criar pedido quando restaurante está inativo")
void naoDeveCriarPedidoQuandoRestauranteInativo() {

    restaurante.setAtivo(false);

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.of(cliente));

    when(restauranteRepository.findById(1L))
            .thenReturn(Optional.of(restaurante));

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals("Restaurante inativo",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve criar pedido quando produto não existe")
void naoDeveCriarPedidoQuandoProdutoNaoExiste() {

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.of(cliente));

    when(restauranteRepository.findById(1L))
            .thenReturn(Optional.of(restaurante));

    when(produtoRepository.findById(1L))
            .thenReturn(Optional.empty());

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals("Produto não encontrado",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve criar pedido quando produto está indisponível")
void naoDeveCriarPedidoQuandoProdutoIndisponivel() {

    produto.setDisponivel(false);

    when(clienteRepository.findById(1L))
            .thenReturn(Optional.of(cliente));

    when(restauranteRepository.findById(1L))
            .thenReturn(Optional.of(restaurante));

    when(produtoRepository.findById(1L))
            .thenReturn(Optional.of(produto));

    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.criarPedido(pedidoDTO));

    assertEquals(
            "Produto indisponível: Pizza Calabresa",
            exception.getMessage());

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve buscar pedido quando ID não existe")
void naoDeveBuscarPedidoQuandoIdNaoExiste() {

    // Arrange
    when(pedidoRepository.findById(999L))
            .thenReturn(Optional.empty());

    // Act + Assert
    RuntimeException exception =
            assertThrows(RuntimeException.class,
                    () -> pedidoService.buscarPedidoPorId(999L));

    assertEquals(
            "Pedido não encontrado",
            exception.getMessage());

    verify(pedidoRepository)
            .findById(999L);
}

@Test
@DisplayName("Deve atualizar status do pedido")
void deveAtualizarStatusPedido() {

    // Arrange

    when(pedidoRepository.findById(1L))
            .thenReturn(Optional.of(pedido));

    when(pedidoRepository.save(any(Pedido.class)))
            .thenReturn(pedido);

    // Act

    PedidoResponseDTO response =
            pedidoService.atualizarStatusPedido(
                    1L,
                    StatusPedido.EM_PREPARACAO);

    // Assert

    assertNotNull(response);

    assertEquals(
            StatusPedido.EM_PREPARACAO,
            response.getStatus());

    verify(pedidoRepository)
            .findById(1L);

    verify(pedidoRepository)
            .save(any(Pedido.class));
}

@Test
@DisplayName("Não deve atualizar status de pedido inexistente")
void naoDeveAtualizarStatusPedidoInexistente() {

    // Arrange

    when(pedidoRepository.findById(999L))
            .thenReturn(Optional.empty());

    // Act + Assert

    RuntimeException exception =
            assertThrows(
                    RuntimeException.class,

                    () -> pedidoService.atualizarStatusPedido(
                            999L,
                            StatusPedido.EM_PREPARACAO));

    assertEquals(
            "Pedido não encontrado",
            exception.getMessage());

    verify(pedidoRepository)
            .findById(999L);

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve cancelar pedido inexistente")
void naoDeveCancelarPedidoInexistente() {

    // Arrange

    when(pedidoRepository.findById(999L))
            .thenReturn(Optional.empty());

    // Act + Assert

    RuntimeException exception =
            assertThrows(
                    RuntimeException.class,

                    () -> pedidoService.cancelarPedido(999L));

    assertEquals(
            "Pedido não encontrado",
            exception.getMessage());

    verify(pedidoRepository)
            .findById(999L);

    verify(pedidoRepository, never())
            .save(any());
}

@Test
@DisplayName("Não deve calcular total quando produto não existe")
void naoDeveCalcularTotalQuandoProdutoNaoExiste() {

    // Arrange

    when(produtoRepository.findById(1L))
            .thenReturn(Optional.empty());

    // Act + Assert

    RuntimeException exception =
            assertThrows(
                    RuntimeException.class,

                    () -> pedidoService.calcularTotalPedido(
                            pedidoDTO.getItens()));

    assertEquals(
            "Produto não encontrado",
            exception.getMessage());

    verify(produtoRepository)
            .findById(1L);
}

@Test
@DisplayName("Deve listar pedidos por cliente")
void deveBuscarPedidosPorCliente() {

    // when

    when(pedidoRepository.findByClienteId(1L))
            .thenReturn(List.of(pedido));

    // execute

    List<PedidoResponseDTO> pedidos =
            pedidoService.buscarPedidosPorCliente(1L);

    // then

    assertNotNull(pedidos);

    assertEquals(1,
            pedidos.size());

    assertEquals(
            "João Silva",
            pedidos.get(0).getNomeCliente());

    verify(pedidoRepository)
            .findByClienteId(1L);
}

}