package com.dev.backend.DTO.requestDTO;

import lombok.Data;

@Data
public class EmailDTO {

    private String referenciaDono;
    private String emailRemetente;
    private String emailDestinatario;
    private String assuntoDoEmail;
    private String corpoTextoDoEmail;
}
