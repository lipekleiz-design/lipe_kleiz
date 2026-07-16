package com.lipe_kleiz.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lipe_kleiz.delivery_api.entity.Produto;
import com.lipe_kleiz.delivery_api.enums.CategoriaProduto;
import com.lipe_kleiz.delivery_api.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ProdutoService")
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setup() {

        produto = new Produto();

        produto.setId(1L);
        produto.setNome("Pizza Calabresa");
        produto.setDescricao("Pizza grande");
        produto.setPreco(new BigDecimal("50.00"));
        produto.setCategoria(CategoriaProduto.PIZZA);
        produto.setDisponivel(true);
    }

    @Test
    @DisplayName("Deve retornar o total de produtos")
    void deveRetornarTotalProdutos() {

        when(produtoRepository.count())
                .thenReturn(10L);

        long total = produtoService.totalProdutos();

        assertEquals(10L, total);

        verify(produtoRepository).count();
    }

    @Test
    @DisplayName("Deve salvar produto")
    void deveSalvarProduto() {

        when(produtoRepository.save(produto))
                .thenReturn(produto);

        Produto resultado =
                produtoService.salvarProduto(produto);

        assertNotNull(resultado);

        assertEquals(
                "Pizza Calabresa",
                resultado.getNome());

        verify(produtoRepository)
                .save(produto);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {

        when(produtoRepository.findAll())
                .thenReturn(List.of(produto));

        List<Produto> produtos =
                produtoService.listarTodos();

        assertNotNull(produtos);

        assertEquals(1,
                produtos.size());

        assertEquals(
                "Pizza Calabresa",
                produtos.get(0).getNome());

        verify(produtoRepository)
                .findAll();
    }

    @Test
    @DisplayName("Deve buscar produto por ID")
    void deveBuscarProdutoPorId() {

        when(produtoRepository.findById(1L))
                .thenReturn(Optional.of(produto));

        Optional<Produto> resultado =
                produtoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());

        assertEquals(
                "Pizza Calabresa",
                resultado.get().getNome());

        verify(produtoRepository)
                .findById(1L);
    }

    @Test
    @DisplayName("Deve excluir produto")
    void deveExcluirProduto() {

        doNothing()
                .when(produtoRepository)
                .deleteById(1L);

        produtoService.excluir(1L);

        verify(produtoRepository)
                .deleteById(1L);
    }

}