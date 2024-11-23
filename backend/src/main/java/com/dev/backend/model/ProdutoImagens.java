package com.dev.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "produto_img")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoImagens {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "idProduto")
    private Produto produto;
}
