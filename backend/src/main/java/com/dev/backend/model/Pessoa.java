package com.dev.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private String senha;
    private String cep;
    private String codigoRecuperarSenha;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataAtualiacao;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataEnvioDoCodigo;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter(value = AccessLevel.NONE)
    private List<PermissaoPessoa> permissaoPessoas;

    // Toda vez que o controller Pessoa receber uma requisição e for criado uma pessoa, automaticamente vai settar o objeto para
    // receber o tipo de permissão
    public void setPermissaoPessoas(List<PermissaoPessoa> permissaoPessoas) {
        for (PermissaoPessoa pessoa : permissaoPessoas) {
            pessoa.setPessoa(this);
        }

        this.permissaoPessoas = permissaoPessoas;
    }
}
