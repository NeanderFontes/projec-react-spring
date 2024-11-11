package com.dev.backend.controller;

import com.dev.backend.model.Produto;
import com.dev.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Produto> listAll() {
        return produtoService.listAll();
    }

    @PostMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Produto criar(@RequestBody Produto criarProduto) {
        return produtoService.criarProduto(criarProduto);
    }

    @PutMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Produto atualizar(@RequestBody Produto atualizarProduto) {
        return produtoService.atualizarProduto(atualizarProduto);
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<Void> eliminar(Long idParaEliminar) {
        produtoService.elminarProduto(idParaEliminar);
        return ResponseEntity.noContent().build();
    }
}
