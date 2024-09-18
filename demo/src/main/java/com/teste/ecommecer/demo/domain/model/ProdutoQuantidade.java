package com.teste.ecommecer.demo.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.List;

@Embeddable
public class ProdutoQuantidade {

    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    @OneToMany (mappedBy = "id")
    private List<Produto> produto;
}
