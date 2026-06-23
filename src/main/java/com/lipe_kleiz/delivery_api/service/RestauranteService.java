package com.lipe_kleiz.delivery_api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lipe_kleiz.delivery_api.dto.RestauranteDTO;
import com.lipe_kleiz.delivery_api.dto.RestauranteResponseDTO;

public interface RestauranteService {

RestauranteResponseDTO cadastrarRestaurante(
        RestauranteDTO dto);

RestauranteResponseDTO buscarRestaurantePorId(
        Long id);

List<RestauranteResponseDTO> listarRestaurantesAtivos();

Page<RestauranteResponseDTO> listarRestaurantes(
        String categoria,
        Boolean ativo,
        Pageable pageable);

RestauranteResponseDTO atualizarRestaurante(
        Long id,
        RestauranteDTO dto);

RestauranteResponseDTO alterarStatusRestaurante(
        Long id);

List<RestauranteResponseDTO> buscarPorCategoria(
        String categoria);

Long totalRestaurantesAtivos();

}
