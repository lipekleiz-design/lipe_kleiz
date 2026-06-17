package com.lipe_kleiz.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lipe_kleiz.delivery_api.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    // Buscar por nome
    Optional<Restaurante> findByNome(String nome);

    // Restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar por categoria
    List<Restaurante> findByCategoriaAndAtivoTrue(String categoria);

    // Buscar por parte do nome
    List<Restaurante> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    // Restaurantes que possuem produtos
    @Query("""
        SELECT DISTINCT r
        FROM Restaurante r
        JOIN r.produtos p
        WHERE r.ativo = true
    """)
    List<Restaurante> findRestaurantesComProdutos();

    // Faixa de taxa de entrega
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

    // Categorias existentes
    @Query("""
        SELECT DISTINCT r.categoria
        FROM Restaurante r
        WHERE r.ativo = true
        ORDER BY r.categoria
    """)
    List<String> findCategoriasDisponiveis();

    // Contagem de restaurantes ativos
    Long countByAtivoTrue();
}