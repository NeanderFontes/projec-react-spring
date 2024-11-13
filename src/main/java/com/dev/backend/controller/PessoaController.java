package com.dev.backend.controller;

import com.dev.backend.model.Pessoa;
import com.dev.backend.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pessoa> listAll() {
        return pessoaService.listAll();
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa criar(@RequestBody Pessoa criarPessoa) {
        return pessoaService.criarPessoa(criarPessoa);
    }

    @PutMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa atualizar(@RequestBody Pessoa atualizarPessoa) {
        return pessoaService.atualizarPessoa(atualizarPessoa);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idParaEliminar) {
        pessoaService.eliminarPessoa(idParaEliminar);
        return ResponseEntity.noContent().build();
    }

}
