package com.lipe_kleiz.delivery_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.model.Pedido;
import com.lipe_kleiz.delivery_api.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public long totalPedidos() {
        return pedidoRepository.count();
    }

    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public void excluir(Long id) {
        pedidoRepository.deleteById(id);
    }
}