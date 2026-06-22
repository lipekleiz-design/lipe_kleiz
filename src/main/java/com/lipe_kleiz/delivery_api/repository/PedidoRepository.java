package com.lipe_kleiz.delivery_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lipe_kleiz.delivery_api.enums.StatusPedido;
import com.lipe_kleiz.delivery_api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por cliente
    List<Pedido> findByClienteId(Long clienteId);

    // Buscar pedidos por restaurante
    List<Pedido> findByRestauranteId(Long restauranteId);

    // Buscar pedidos por status
    List<Pedido> findByStatus(StatusPedido status);

}