package com.dev.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "cidade")
@Data
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idEstado")
    private Estado estado;

    private String nome;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataAtualizacao;
}
