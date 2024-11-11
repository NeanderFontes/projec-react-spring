package com.dev.backend.controller;

import com.dev.backend.model.ProdutoImagens;
import com.dev.backend.service.ProdutoImgService;
import com.dev.backend.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/produto-img")
public class ProdutoImgController {

    @Autowired
    private ProdutoImgService produtoImgService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoImagens> listAll() {
        return produtoImgService.listAll();
    }

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProdutoImagens criar(@RequestParam("idProduto") Long idProduto,
                                @RequestParam("file") MultipartFile file) {
        return produtoImgService.criarImg(idProduto, file);
    }

    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProdutoImagens atualizar(@RequestParam("idProduto") Long idProduto,
                                    @PathVariable("id") Long idImg,
                                    @RequestParam("file") MultipartFile file) {
        return produtoImgService.atualizarImg(idProduto, idImg, file);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idParaEliminarImg) {
        produtoImgService.eliminar(idParaEliminarImg);
        return ResponseEntity.noContent().build();
    }
}
