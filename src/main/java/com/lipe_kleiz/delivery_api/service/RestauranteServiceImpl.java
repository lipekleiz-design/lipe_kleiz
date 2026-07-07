package com.lipe_kleiz.delivery_api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.dto.RestauranteDTO;
import com.lipe_kleiz.delivery_api.dto.RestauranteResponseDTO;
import com.lipe_kleiz.delivery_api.entity.Restaurante;
import com.lipe_kleiz.delivery_api.repository.RestauranteRepository;

@Service
public class RestauranteServiceImpl implements RestauranteService {

private final RestauranteRepository restauranteRepository;

public RestauranteServiceImpl(
        RestauranteRepository restauranteRepository) {

    this.restauranteRepository = restauranteRepository;
}

@Override
public RestauranteResponseDTO cadastrarRestaurante(
        RestauranteDTO dto) {

    Restaurante restaurante = new Restaurante();

    restaurante.setNome(dto.getNome());
    restaurante.setCategoria(dto.getCategoria());
    restaurante.setEndereco(dto.getEndereco());
    restaurante.setTelefone(dto.getTelefone());
    restaurante.setTaxaEntrega(dto.getTaxaEntrega());
    restaurante.setAtivo(true);

    Restaurante salvo =
            restauranteRepository.save(restaurante);

    return converter(salvo);
}

@Override
public RestauranteResponseDTO buscarRestaurantePorId(
        Long id) {

    Restaurante restaurante =
            restauranteRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Restaurante não encontrado"));

    return converter(restaurante);
}

@Override
public List<RestauranteResponseDTO>
        listarRestaurantesAtivos() {

    return restauranteRepository.findByAtivoTrue()
            .stream()
            .map(this::converter)
            .toList();
}

@Override
public Page<RestauranteResponseDTO> listarRestaurantes(
        String categoria,
        Boolean ativo,
        Pageable pageable) {

    Page<Restaurante> pagina;

    boolean somenteAtivos =
            ativo == null || ativo;

    if (categoria != null &&
            !categoria.isBlank() &&
            somenteAtivos) {

        pagina =
                restauranteRepository
                        .findByCategoriaAndAtivoTrue(
                                categoria,
                                pageable);

    } else {

        pagina =
                restauranteRepository
                        .findByAtivoTrue(pageable);
    }

    return pagina.map(this::converter);
}

@Override
public RestauranteResponseDTO atualizarRestaurante(
        Long id,
        RestauranteDTO dto) {

    Restaurante restaurante =
            restauranteRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Restaurante não encontrado"));

    restaurante.setNome(dto.getNome());
    restaurante.setCategoria(dto.getCategoria());
    restaurante.setEndereco(dto.getEndereco());
    restaurante.setTelefone(dto.getTelefone());
    restaurante.setTaxaEntrega(dto.getTaxaEntrega());

    Restaurante atualizado =
            restauranteRepository.save(restaurante);

    return converter(atualizado);
}

@Override
public RestauranteResponseDTO alterarStatusRestaurante(
        Long id) {

    Restaurante restaurante =
            restauranteRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Restaurante não encontrado"));

    restaurante.setAtivo(
            !restaurante.isAtivo());

    Restaurante atualizado =
            restauranteRepository.save(restaurante);

    return converter(atualizado);
}

@Override
public List<RestauranteResponseDTO>
        buscarPorCategoria(
                String categoria) {

    return restauranteRepository
            .findByCategoriaAndAtivoTrue(
                    categoria)
            .stream()
            .map(this::converter)
            .toList();
}

@Override
public Long totalRestaurantesAtivos() {

    return restauranteRepository
            .countByAtivoTrue();
}

private RestauranteResponseDTO converter(
        Restaurante restaurante) {

    RestauranteResponseDTO dto =
            new RestauranteResponseDTO();

    dto.setId(restaurante.getId());
    dto.setNome(restaurante.getNome());
    dto.setCategoria(restaurante.getCategoria());
    dto.setEndereco(restaurante.getEndereco());
    dto.setTelefone(restaurante.getTelefone());
    dto.setTaxaEntrega(restaurante.getTaxaEntrega());
    dto.setAtivo(restaurante.isAtivo());

    return dto;
}

}
