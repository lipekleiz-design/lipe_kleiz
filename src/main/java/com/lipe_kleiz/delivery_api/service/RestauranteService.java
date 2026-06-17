package com.lipe_kleiz.delivery_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.model.Restaurante;
import com.lipe_kleiz.delivery_api.repository.RestauranteRepository;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public long totalRestaurantes() {
        return restauranteRepository.count();
    }

    public Restaurante salvarRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    public void excluir(Long id) {
        restauranteRepository.deleteById(id);
    }
}