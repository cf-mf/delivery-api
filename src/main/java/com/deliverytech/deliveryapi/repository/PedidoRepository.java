package com.deliverytech.deliveryapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.deliveryapi.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar por número do pedido
    Optional<Pedido> findByNumeroPedido(String numeroPedido);

    // Buscar por status
    List<Pedido> findByStatusIgnoreCase(String status);

    // Buscar pedidos de um cliente
    List<Pedido> findByClienteId(Long clienteId);

    // Buscar pedidos de um restaurante
    List<Pedido> findByRestauranteId(Long restauranteId);
}
