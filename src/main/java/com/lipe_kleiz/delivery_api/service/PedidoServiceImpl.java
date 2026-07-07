package com.lipe_kleiz.delivery_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            RestauranteRepository restauranteRepository,
            ProdutoRepository produtoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public PedidoResponseDTO criarPedido(PedidoDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado"));

        Restaurante restaurante = restauranteRepository
                .findById(dto.getRestauranteId())
                .orElseThrow(() ->
                        new RuntimeException("Restaurante não encontrado"));

        if (!cliente.isAtivo()) {
            throw new RuntimeException("Cliente inativo");
        }

        if (!restaurante.isAtivo()) {
            throw new RuntimeException("Restaurante inativo");
        }

        Pedido pedido = new Pedido();

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setTaxaEntrega(restaurante.getTaxaEntrega());
        pedido.setStatus(StatusPedido.PENDENTE);

        List<ItemPedido> itens = new ArrayList<>();

        for (ItemPedidoDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository
                    .findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Produto não encontrado"));

            if (!produto.isDisponivel()) {
                throw new RuntimeException(
                        "Produto indisponível: "
                                + produto.getNome());
            }

            ItemPedido item = new ItemPedido();

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            itens.add(item);
        }

        pedido.setItens(itens);

        Pedido pedidoSalvo =
                pedidoRepository.save(pedido);

        return converterParaDTO(pedidoSalvo);
    }

    @Override
    public PedidoResponseDTO buscarPedidoPorId(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"));

        return converterParaDTO(pedido);
    }

    @Override
    public List<PedidoResponseDTO> buscarPedidosPorCliente(
            Long clienteId) {

        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO atualizarStatusPedido(
            Long id,
            StatusPedido status) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"));

        pedido.setStatus(status);

        Pedido atualizado =
                pedidoRepository.save(pedido);

        return converterParaDTO(atualizado);
    }

    @Override
    public BigDecimal calcularTotalPedido(
            List<ItemPedidoDTO> itens) {

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoDTO item : itens) {

            Produto produto = produtoRepository
                    .findById(item.getProdutoId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Produto não encontrado"));

            total = total.add(
                    produto.getPreco().multiply(
                            BigDecimal.valueOf(
                                    item.getQuantidade())));
        }

        return total;
    }

    @Override
    public void cancelarPedido(Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Pedido não encontrado"));

        pedido.setStatus(StatusPedido.CANCELADO);

        pedidoRepository.save(pedido);
    }

    private PedidoResponseDTO converterParaDTO(
            Pedido pedido) {

        PedidoResponseDTO dto =
                new PedidoResponseDTO();

        dto.setId(pedido.getId());

        dto.setClienteId(
                pedido.getCliente().getId());

        dto.setNomeCliente(
                pedido.getCliente().getNome());

        dto.setRestauranteId(
                pedido.getRestaurante().getId());

        dto.setNomeRestaurante(
                pedido.getRestaurante().getNome());

        dto.setEnderecoEntrega(
                pedido.getEnderecoEntrega());

        dto.setTaxaEntrega(
                pedido.getTaxaEntrega());

        dto.setSubtotal(
                pedido.getSubtotal());

        dto.setValorTotal(
                pedido.getValorTotal());

        dto.setStatus(
                pedido.getStatus());

        dto.setDataPedido(
                pedido.getDataPedido());

        return dto;
    }
}