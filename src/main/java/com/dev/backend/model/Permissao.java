package com.dev.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "permissao")
@Data
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
}
