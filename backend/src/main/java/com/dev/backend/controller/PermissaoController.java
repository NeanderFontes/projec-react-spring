package com.dev.backend.controller;

import com.dev.backend.model.Permissao;
import com.dev.backend.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/permissao")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Permissao> listAll() {
        return permissaoService.listAll();
    }

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Permissao criar(@RequestBody Permissao criarPermissao) {
        return permissaoService.crarPermissao(criarPermissao);
    }


    @PutMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Permissao atualizar(@RequestBody Permissao atualizarPermissao) {
        return permissaoService.attPermisao(atualizarPermissao);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idEliminar) {
        permissaoService.eliminarPermisao(idEliminar);
        return ResponseEntity.noContent().build();
    }
}
