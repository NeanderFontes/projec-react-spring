package com.dev.backend.DTO.requestDTO;

import com.dev.backend.model.Pessoa;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class DadosPessoaGerenciarSenhasDTO {
    //TODO add validantion @NotBlank
    private String codigoRecuperarSenha;
    private String email;
    private String senha;
    private Date dataCriacao;

    // Método para converter DTO em Model
    public Pessoa converterDTOToModel (DadosPessoaGerenciarSenhasDTO dadosPessoaGerenciarSenhasDTO) {
        Pessoa pessoaModel = new Pessoa();
        BeanUtils.copyProperties(dadosPessoaGerenciarSenhasDTO, pessoaModel);
        return pessoaModel;
    }

}
