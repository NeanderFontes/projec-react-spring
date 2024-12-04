package com.dev.backend.controller;

import com.dev.backend.DTO.requestDTO.CategoriaRequestDTO;
import com.dev.backend.model.Categoria;
import com.dev.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin
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
    public Categoria criar(@RequestBody CategoriaRequestDTO criarCategoriaDTO) {
        return categoriaService.criarCategoria(criarCategoriaDTO);
    }

    @PutMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Categoria atualizar(@RequestBody CategoriaRequestDTO atualizarCategoriaDTO) {
        return categoriaService.atualizarCategoria(atualizarCategoriaDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> elminar(@PathVariable("id") Long idParaEliminar) {
        categoriaService.eliminarCategoria(idParaEliminar);
        return ResponseEntity.noContent().build();
    }
}
