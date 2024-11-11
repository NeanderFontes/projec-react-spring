package com.dev.backend.controller;

import com.dev.backend.model.Categoria;
import com.dev.backend.service.CategoriaService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Categoria> listAll() {
        return categoriaService.listAll();
    }

    @PostMapping(value = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Categoria criar(@RequestBody Categoria criarCategoria) {
        return categoriaService.criarCategoria(criarCategoria);
    }

    @PutMapping(value = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Categoria atualizar(@RequestBody Categoria atualizarCategoria) {
        return categoriaService.atualizarCategoria(atualizarCategoria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> elminar(@PathVariable("id") Long idParaEliminar) {
        categoriaService.eliminarCategoria(idParaEliminar);
        return ResponseEntity.noContent().build();
    }
}
