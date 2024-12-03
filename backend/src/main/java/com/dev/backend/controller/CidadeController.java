package com.dev.backend.controller;

import com.dev.backend.exceptions.exHandler.GlobalExceptionHandler;
import com.dev.backend.model.Cidade;
import com.dev.backend.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/cidade")
@CrossOrigin
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listAll() {
        return cidadeService.listAll();
    }

    @Transactional
    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cidade> criar(@RequestBody Cidade criarCidade,
                                        UriComponentsBuilder uriComponentsBuilder) {
        // Chama o service para salvar a entidade
        Cidade cidadeSalva = cidadeService.criarCidade(criarCidade);

        // Variável URI encapsulada com Location('endereço ou Header') para FrontEnd
        var uri = uriComponentsBuilder.path("/cidade/{id}").buildAndExpand(cidadeSalva.getId()).toUri();

        // Response HttpStatus CREATED com URI e BODY do Objeto novo criado
        return ResponseEntity.created(uri).body(cidadeSalva);
    }

    @PutMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cidade atualizar(@RequestBody Cidade atualizarCidade) {
        return cidadeService.atualizarCidade(atualizarCidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long idCidade) {
        cidadeService.eliminarCidaide(idCidade);
        return ResponseEntity.noContent().build();
    }
}
