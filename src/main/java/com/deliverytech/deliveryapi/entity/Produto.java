package com.deliverytech.deliveryapi.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    private String categoria;

    private Boolean disponivel;

    // Relacionamento com Restaurante
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    // Métodos auxiliares
    public void ativar() {
        this.disponivel = true;
    }

    public void inativar() {
        this.disponivel = false;
    }
}
