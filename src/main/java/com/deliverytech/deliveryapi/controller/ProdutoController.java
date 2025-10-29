package com.deliverytech.deliveryapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.deliveryapi.entity.Produto;
import com.deliverytech.deliveryapi.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Cadastrar novo produto
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.cadastrar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Listar produtos disponíveis
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> produtos = produtoService.listarDisponiveis();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar produto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Atualizar produto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody Produto produto) {
        try {
            Produto atualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Inativar produto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        try {
            produtoService.inativar(id);
            return ResponseEntity.ok("Produto inativado com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }

    /**
     * Buscar produtos por nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar produtos por categoria
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Buscar produtos por restaurante
     */
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Produto>> buscarPorRestaurante(@PathVariable Long restauranteId) {
        List<Produto> produtos = produtoService.buscarPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }
}
