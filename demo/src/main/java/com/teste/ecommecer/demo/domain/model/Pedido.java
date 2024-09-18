package com.teste.ecommecer.demo.domain.model;


import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

@Entity
@Table (name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    @Embedded
    private ProdutoQuantidade produtoQuantidade;

}
