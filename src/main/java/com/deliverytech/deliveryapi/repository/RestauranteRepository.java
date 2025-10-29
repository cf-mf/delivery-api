package com.deliverytech.deliveryapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.deliveryapi.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    // Buscar restaurantes ativos
    List<Restaurante> findByAtivoTrue();

    // Buscar restaurantes por nome (contendo, ignorando maiúsculas/minúsculas)
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    // Buscar restaurantes por categoria
    List<Restaurante> findByCategoriaIgnoreCase(String categoria);

    // Buscar restaurantes por categoria e ativos (opcional, se quiser filtrar os dois juntos)
    List<Restaurante> findByCategoriaIgnoreCaseAndAtivoTrue(String categoria);
}
