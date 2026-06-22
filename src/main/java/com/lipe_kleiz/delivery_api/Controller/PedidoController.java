package com.lipe_kleiz.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lipe_kleiz.delivery_api.dto.ItemPedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoDTO;
import com.lipe_kleiz.delivery_api.dto.PedidoResponseDTO;
import com.lipe_kleiz.delivery_api.dto.StatusPedidoDTO;
import com.lipe_kleiz.delivery_api.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

private final PedidoService pedidoService;

public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
}

@PostMapping
public ResponseEntity<PedidoResponseDTO> criarPedido(
        @Valid @RequestBody PedidoDTO dto) {

    PedidoResponseDTO pedido = pedidoService.criarPedido(dto);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(pedido);
}

@GetMapping("/{id}")
public ResponseEntity<PedidoResponseDTO> buscarPorId(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            pedidoService.buscarPedidoPorId(id));
}

@GetMapping("/cliente/{clienteId}")
public ResponseEntity<List<PedidoResponseDTO>> buscarPorCliente(
        @PathVariable Long clienteId) {

    return ResponseEntity.ok(
            pedidoService.buscarPedidosPorCliente(clienteId));
}

@PatchMapping("/{id}/status")
public ResponseEntity<PedidoResponseDTO> atualizarStatus(
        @PathVariable Long id,
        @Valid @RequestBody StatusPedidoDTO statusDTO) {

    return ResponseEntity.ok(
            pedidoService.atualizarStatusPedido(
                    id,
                    statusDTO.getStatus()));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> cancelarPedido(
        @PathVariable Long id) {

    pedidoService.cancelarPedido(id);

    return ResponseEntity.noContent().build();
}

@PostMapping("/calcular")
public ResponseEntity<BigDecimal> calcularTotal(
        @Valid @RequestBody List<ItemPedidoDTO> itens) {

    return ResponseEntity.ok(
            pedidoService.calcularTotalPedido(itens));
}

}
