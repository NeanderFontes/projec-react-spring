package com.dev.backend.controller;

import com.dev.backend.model.Cidade;
import com.dev.backend.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cidade")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping("/")
    public List<Cidade> listAll() {
        return cidadeService.listAll();
    }

    @PostMapping("/")
    public Cidade criar(@RequestBody Cidade criarCidade) {
        return cidadeService.criarCidade(criarCidade);
    }

    @PutMapping("/")
    public Cidade atualizar(@RequestBody Cidade atualizarCidade) {
        return cidadeService.atualizarCidade(atualizarCidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idCidade) {
        cidadeService.eliminarCidaide(idCidade);
        return ResponseEntity.noContent().build();
    }
}
