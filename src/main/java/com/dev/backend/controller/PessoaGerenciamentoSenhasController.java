package com.dev.backend.controller;

import com.dev.backend.DTO.requestDTO.DadosPessoaClienteCadastroRequestDTO;
import com.dev.backend.DTO.requestDTO.DadosPessoaGerenciarSenhasDTO;
import com.dev.backend.model.Pessoa;
import com.dev.backend.service.EmailService;
import com.dev.backend.service.PessoaGerenciamentoSenhasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Define a classe como um controlador REST
@RestController

/**
 * Controller para gerenciamento de Senhas, tanto para Cliente como Funcionario
 */
@RequestMapping("/api/gestao-senhas")
public class PessoaGerenciamentoSenhasController {

    @Autowired
    private PessoaGerenciamentoSenhasService pessoaGerenciamentoSenhasService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/solicitar-codigo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> criarCodigoSenha(@RequestBody DadosPessoaGerenciarSenhasDTO dadosPessoaGerenciarSenhasDTO) {
        Pessoa pessoa = new DadosPessoaGerenciarSenhasDTO().converterDTOToModel(dadosPessoaGerenciarSenhasDTO);
        String pessoaString = pessoaGerenciamentoSenhasService.solicitarCodigo(pessoa.getEmail());
        return new ResponseEntity<>(pessoaString, HttpStatus.CREATED);
    }

    @PostMapping(value = "/alterar-senha",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> alterarSenha(@RequestBody DadosPessoaGerenciarSenhasDTO dadosPessoaGerenciarSenhasDTO) {
        Pessoa pessoa = new DadosPessoaGerenciarSenhasDTO().converterDTOToModel(dadosPessoaGerenciarSenhasDTO);
        String pessoaString = pessoaGerenciamentoSenhasService.alterarSenha(pessoa);
        return new ResponseEntity<>(pessoaString, HttpStatus.CREATED);
    }
}