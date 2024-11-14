package com.dev.backend.controller;

import com.dev.backend.DTO.requestDTO.DadosPessoaClienteCadastroRequestDTO;
import com.dev.backend.model.Pessoa;
import com.dev.backend.service.DadosPessoaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Define a classe como um controlador REST
@RestController

// Define o endpoint base para a API
@RequestMapping("/api/cliente-cadastro")
public class DadosPessoaClienteController {

    // Injeta automaticamente a dependência do serviço
    @Autowired
    private DadosPessoaClienteService dadosPessoaClienteService;

    @PostMapping(value = "/", // Define que o método responderá a requisições POST no endpoint base
            consumes = MediaType.APPLICATION_JSON_VALUE, // Define que a requisição deve ter JSON como conteúdo
            produces = MediaType.APPLICATION_JSON_VALUE) // Define que a resposta será retornada em JSON
    public Pessoa registar(@RequestBody DadosPessoaClienteCadastroRequestDTO dadosPessoaClienteCadastroRequestDTO) {
        // Recebe um objeto DTO da requisição e chama o serviço para processar o registro do cliente
        return dadosPessoaClienteService.registarDadoPessoaCliente(dadosPessoaClienteCadastroRequestDTO);
    }
}
