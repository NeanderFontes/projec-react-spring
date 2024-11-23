package com.dev.backend.controller;

import com.dev.backend.DTO.requestDTO.DadosPessoaClienteCadastroRequestDTO;
import com.dev.backend.model.Pessoa;
import com.dev.backend.service.DadosPessoaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Define a classe como um controlador REST
@RestController

// Define o endpoint base para a API
@RequestMapping("/api/cliente")
public class DadosPessoaClienteController {

    @Autowired
    private DadosPessoaClienteService dadosPessoaClienteService;

    @PostMapping(value = "/registo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pessoa> registar(@RequestBody DadosPessoaClienteCadastroRequestDTO dadosPessoaClienteCadastroRequestDTO) {
        Pessoa pessoa = dadosPessoaClienteService.registarDadoPessoaCliente(dadosPessoaClienteCadastroRequestDTO);
        return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
    }
}