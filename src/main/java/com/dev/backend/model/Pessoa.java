package com.dev.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCidade")
    private Cidade cidade;

    private String nome;
    private String cpf;
    private String email;
    private String cep;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataAtualiacao;
}
