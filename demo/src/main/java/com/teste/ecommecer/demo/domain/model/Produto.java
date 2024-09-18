package com.teste.ecommecer.demo.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table (name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private BigDecimal valorProduto;


}
