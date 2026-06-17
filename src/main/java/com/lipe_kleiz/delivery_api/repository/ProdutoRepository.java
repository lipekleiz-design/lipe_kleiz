package com.lipe_kleiz.delivery_api.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lipe_kleiz.delivery_api.enums.CategoriaProduto;
import com.lipe_kleiz.delivery_api.model.Produto;
import com.lipe_kleiz.delivery_api.model.Restaurante;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Produtos disponíveis de um restaurante
    List<Produto> findByRestauranteAndDisponivelTrue(
            Restaurante restaurante);

    // Produtos disponíveis por ID do restaurante
    List<Produto> findByRestauranteIdAndDisponivelTrue(
            Long restauranteId);

    // Produtos por categoria
    List<Produto> findByCategoriaAndDisponivelTrue(
            CategoriaProduto categoria);

    // Busca por nome
    List<Produto> findByNomeContainingIgnoreCaseAndDisponivelTrue(
            String nome);

    // Faixa de preço
    List<Produto> findByPrecoBetweenAndDisponivelTrue(
            BigDecimal precoMin,
            BigDecimal precoMax);

    // Menor ou igual a determinado preço
    List<Produto> findByPrecoLessThanEqualAndDisponivelTrue(
            BigDecimal preco);

    // Ordenação por preço
    List<Produto> findByDisponivelTrueOrderByPrecoAsc();

    List<Produto> findByDisponivelTrueOrderByPrecoDesc();

    // Contagem de produtos disponíveis do restaurante
    Long countByRestauranteIdAndDisponivelTrue(
            Long restauranteId);
}