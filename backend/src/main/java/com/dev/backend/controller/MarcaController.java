package com.dev.backend.controller;

import com.dev.backend.DTO.requestDTO.MarcaRequestDTO;
import com.dev.backend.model.Marca;
import com.dev.backend.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/marca")
@CrossOrigin
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Marca> listAll() {
       return marcaService.listAll();
    }

    @PostMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Marca criar(@RequestBody MarcaRequestDTO criarMarcaDTO) {
        return marcaService.criarMarca(criarMarcaDTO);
    }

    @PutMapping(value = "/",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public Marca atualizar(@RequestBody MarcaRequestDTO atualizarMarcaDTO) {
        return marcaService.atualizarMarca(atualizarMarcaDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long delMarcaId) {
        marcaService.eliminarMarca(delMarcaId);
        return ResponseEntity.noContent().build();
    }
}
