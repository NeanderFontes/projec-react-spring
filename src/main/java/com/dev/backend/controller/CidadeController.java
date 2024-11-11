package com.dev.backend.controller;

import com.dev.backend.model.Cidade;
import com.dev.backend.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listAll() {
        return cidadeService.listAll();
    }

    @PostMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cidade criar(@RequestBody Cidade criarCidade) {
        return cidadeService.criarCidade(criarCidade);
    }

    @PutMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cidade atualizar(@RequestBody Cidade atualizarCidade) {
        return cidadeService.atualizarCidade(atualizarCidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idCidade) {
        cidadeService.eliminarCidaide(idCidade);
        return ResponseEntity.noContent().build();
    }
}
