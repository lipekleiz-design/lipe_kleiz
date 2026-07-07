package com.lipe_kleiz.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lipe_kleiz.delivery_api.entity.Restaurante;

public interface RestauranteRepository
extends JpaRepository<Restaurante, Long> {

Optional<Restaurante> findByNome(String nome);

List<Restaurante> findByAtivoTrue();

Page<Restaurante> findByAtivoTrue(Pageable pageable);

List<Restaurante> findByCategoriaAndAtivoTrue(
        String categoria);

Page<Restaurante> findByCategoriaAndAtivoTrue(
        String categoria,
        Pageable pageable);

List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(
        String nome);

@Query("""
    SELECT DISTINCT r
    FROM Restaurante r
    JOIN r.produtos p
    WHERE r.ativo = true
""")
List<Restaurante> findRestaurantesComProdutos();

@Query("""
    SELECT r
    FROM Restaurante r
    WHERE r.taxaEntrega BETWEEN :min AND :max
    AND r.ativo = true
""")
List<Restaurante> findByTaxaEntregaBetween(
        @Param("min") BigDecimal min,
        @Param("max") BigDecimal max
);

@Query("""
    SELECT DISTINCT r.categoria
    FROM Restaurante r
    WHERE r.ativo = true
    ORDER BY r.categoria
""")
List<String> findCategoriasDisponiveis();

Long countByAtivoTrue();

}
