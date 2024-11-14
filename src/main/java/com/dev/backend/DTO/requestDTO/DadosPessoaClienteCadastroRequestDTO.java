package com.dev.backend.DTO.requestDTO;

import com.dev.backend.model.Cidade;
import com.dev.backend.model.Pessoa;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class DadosPessoaClienteCadastroRequestDTO {

    //TODO implentar Validações?
    private Cidade cidade;
    private String nome;
    private String cpf;
    private String email;
    private String cep;

    // Método para converter o DTO em um modelo de entidade Pessoa
    public Pessoa converterDTOParaModel(DadosPessoaClienteCadastroRequestDTO dadosPessoaClienteCadastroRequestDTO) {
        // Cria um novo objeto Pessoa e copia as propriedades do DTO para o modelo
        Pessoa pessoaEntityModelDB = new Pessoa();
        BeanUtils.copyProperties(dadosPessoaClienteCadastroRequestDTO, pessoaEntityModelDB);
        return pessoaEntityModelDB;
    }
}
