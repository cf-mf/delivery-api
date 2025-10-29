package com.deliverytech.deliveryapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.deliveryapi.entity.Pedido;
import com.deliverytech.deliveryapi.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Criar novo pedido
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Pedido pedido) {
        try {
            Pedido pedidoSalvo = pedidoService.cadastrar(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Listar todos os pedidos
     */
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Buscar por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);

        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar por número do pedido
     */
    @GetMapping("/numero/{numeroPedido}")
    public ResponseEntity<?> buscarPorNumero(@PathVariable String numeroPedido) {
        Optional<Pedido> pedido = pedidoService.buscarPorNumero(numeroPedido);

        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar pedidos por status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPorStatus(@PathVariable String status) {
        List<Pedido> pedidos = pedidoService.buscarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Buscar pedidos de um cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Buscar pedidos de um restaurante
     */
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Pedido>> buscarPorRestaurante(@PathVariable Long restauranteId) {
        List<Pedido> pedidos = pedidoService.buscarPorRestaurante(restauranteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Atualizar pedido
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Validated @RequestBody Pedido pedido) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizar(id, pedido);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Excluir pedido (delete físico)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            pedidoService.excluir(id);
            return ResponseEntity.ok("Pedido excluído com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
}
