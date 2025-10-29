package com.deliverytech.deliveryapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.deliveryapi.entity.Pedido;
import com.deliverytech.deliveryapi.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Cadastrar novo pedido
     */
    public Pedido cadastrar(Pedido pedido) {
        validarPedido(pedido);

        pedido.setDataPedido(LocalDateTime.now());
        if (pedido.getStatus() == null) {
            pedido.setStatus("PENDENTE");
        }

        return pedidoRepository.save(pedido);
    }

    /**
     * Buscar por ID
     */
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    /**
     * Buscar por número
     */
    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorNumero(String numeroPedido) {
        return pedidoRepository.findByNumeroPedido(numeroPedido);
    }

    /**
     * Listar todos
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    /**
     * Buscar por status
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorStatus(String status) {
        return pedidoRepository.findByStatusIgnoreCase(status);
    }

    /**
     * Buscar por cliente
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    /**
     * Buscar por restaurante
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPorRestaurante(Long restauranteId) {
        return pedidoRepository.findByRestauranteId(restauranteId);
    }

    /**
     * Atualizar pedido 
     */
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));

        if (pedidoAtualizado.getStatus() != null) {
            pedido.setStatus(pedidoAtualizado.getStatus());
        }

        pedido.setObservacoes(pedidoAtualizado.getObservacoes());
        pedido.setItens(pedidoAtualizado.getItens());
        pedido.setValorTotal(pedidoAtualizado.getValorTotal());

        return pedidoRepository.save(pedido);
    }

    /**
     * Excluir pedido (delete físico)
     */
    public void excluir(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Pedido não encontrado: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    /**
     * Validação básica
     */
    private void validarPedido(Pedido pedido) {
        if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().trim().isEmpty()) {
            throw new IllegalArgumentException("Número do pedido é obrigatório");
        }

        if (pedido.getCliente() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }

        if (pedido.getRestaurante() == null) {
            throw new IllegalArgumentException("Restaurante é obrigatório");
        }

        if (pedido.getValorTotal() == null || pedido.getValorTotal().doubleValue() < 0) {
            throw new IllegalArgumentException("Valor total inválido");
        }
    }
}
