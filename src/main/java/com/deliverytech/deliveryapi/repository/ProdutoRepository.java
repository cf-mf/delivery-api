package com.deliverytech.deliveryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.deliveryapi.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos disponíveis
    List<Produto> findByDisponivelTrue();

    // Buscar produtos por nome
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Buscar produtos por categoria
    List<Produto> findByCategoriaIgnoreCase(String categoria);

    // Buscar produtos por restaurante
    List<Produto> findByRestauranteId(Long restauranteId);
}
