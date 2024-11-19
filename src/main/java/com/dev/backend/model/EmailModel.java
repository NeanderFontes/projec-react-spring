package com.dev.backend.model;

import com.dev.backend.enums.StatusEmailEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "tb_email")
@Data
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String referenciaDono;
    private String emailRemetente;
    private String emailDestinatario;
    private String assuntoDoEmail;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String corpoTextoDoEmail;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dataEnvioEmail;

    @Enumerated(EnumType.ORDINAL)
    private StatusEmailEnum statusEmail;
}
