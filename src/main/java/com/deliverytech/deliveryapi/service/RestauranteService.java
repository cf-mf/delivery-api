package com.deliverytech.deliveryapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.deliveryapi.entity.Restaurante;
import com.deliverytech.deliveryapi.repository.RestauranteRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Cadastrar novo restaurante
     */
    public Restaurante cadastrar(Restaurante restaurante) {
        validarDadosRestaurante(restaurante);

        // Define como ativo por padrão
        restaurante.setAtivo(true);

        return restauranteRepository.save(restaurante);
    }

    /**
     * Buscar restaurante por ID
     */
    @Transactional(readOnly = true)
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    /**
     * Listar todos os restaurantes ativos
     */
    @Transactional(readOnly = true)
    public List<Restaurante> listarAtivos() {
        return restauranteRepository.findByAtivoTrue();
    }

    /**
     * Atualizar dados do restaurante
     */
    public Restaurante atualizar(Long id, Restaurante restauranteAtualizado) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        // Atualizar campos
        restaurante.setNome(restauranteAtualizado.getNome());
        restaurante.setCategoria(restauranteAtualizado.getCategoria());
        restaurante.setEndereco(restauranteAtualizado.getEndereco());
        restaurante.setTelefone(restauranteAtualizado.getTelefone());
        restaurante.setTaxaEntrega(restauranteAtualizado.getTaxaEntrega());
        restaurante.setAvaliacao(restauranteAtualizado.getAvaliacao());

        return restauranteRepository.save(restaurante);
    }

    /**
     * Inativar restaurante (soft delete)
     */
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurante.inativar();
        restauranteRepository.save(restaurante);
    }

    /**
     * Buscar restaurantes por nome
     */
    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorNome(String nome) {
        return restauranteRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Buscar restaurantes por categoria
     */
    @Transactional(readOnly = true)
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoriaIgnoreCase(categoria);
    }

    /**
     * Listar endereços de todos os restaurantes ativos
     */
    @Transactional(readOnly = true)
    public List<String> listarEnderecos() {
        // Retorna apenas o campo de endereço de cada restaurante ativo
        return restauranteRepository.findByAtivoTrue()
                .stream()
                .map(Restaurante::getEndereco)
                .toList();
    }

    /**
     * Validações de negócio
     */
    private void validarDadosRestaurante(Restaurante restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (restaurante.getNome().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }

        if (restaurante.getCategoria() == null || restaurante.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        if (restaurante.getTaxaEntrega() != null && restaurante.getTaxaEntrega() < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa");
        }

        if (restaurante.getAvaliacao() != null &&
                (restaurante.getAvaliacao() < 0 || restaurante.getAvaliacao() > 5)) {
            throw new IllegalArgumentException("Avaliação deve estar entre 0 e 5");
        }
    }
}