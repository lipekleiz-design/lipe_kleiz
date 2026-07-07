package com.lipe_kleiz.delivery_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lipe_kleiz.delivery_api.entity.Produto;
import com.lipe_kleiz.delivery_api.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public long totalProdutos() {
        return produtoRepository.count();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }
}