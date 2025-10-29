package com.deliverytech.deliveryapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String categoria;

    private String endereco;
    
    private String telefone;

    @Column(name = "taxa_entrega")
    private Double taxaEntrega;

    private Double avaliacao;

    @Column(nullable = true)
    private Boolean ativo;

    public void inativar() {
        this.ativo = false;
    }
}
