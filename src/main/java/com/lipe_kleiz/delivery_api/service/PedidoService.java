package com.lipe_kleiz.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import com.lipe_kleiz.delivery_api.dto.ItemPedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoResponseDTO;
import com.lipe_kleiz.delivery_api.enums.StatusPedido;

public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoDTO dto);

    PedidoResponseDTO buscarPedidoPorId(Long id);

    List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId);

    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens);

    void cancelarPedido(Long id);
}