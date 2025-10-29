package com.deliverytech.deliveryapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.deliveryapi.entity.Produto;
import com.deliverytech.deliveryapi.repository.ProdutoRepository;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cadastrar novo produto
     */
    public Produto cadastrar(Produto produto) {
        validarProduto(produto);

        // Disponível por padrão
        produto.setDisponivel(true);

        return produtoRepository.save(produto);
    }

    /**
     * Buscar produto por ID
     */
    @Transactional(readOnly = true)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    /**
     * Listar produtos disponíveis
     */
    @Transactional(readOnly = true)
    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponivelTrue();
    }

    /**
     * Atualizar produto
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(produtoAtualizado.getCategoria());
        produto.setDisponivel(produtoAtualizado.getDisponivel());

        return produtoRepository.save(produto);
    }

    /**
     * Inativar produto (soft delete)
     */
    public void inativar(Long id) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        produto.inativar();
        produtoRepository.save(produto);
    }

    /**
     * Buscar produtos por nome
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Buscar produtos por categoria
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaIgnoreCase(categoria);
    }

    /**
     * Buscar produtos por restaurante
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    /**
     * Validação simples
     */
    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco().doubleValue() < 0) {
            throw new IllegalArgumentException("Preço inválido");
        }

        if (produto.getRestaurante() == null) {
            throw new IllegalArgumentException("O produto deve estar vinculado a um restaurante");
        }
    }
}
