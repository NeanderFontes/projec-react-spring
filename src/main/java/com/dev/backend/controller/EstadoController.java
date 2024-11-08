package com.dev.backend.controller;

import com.dev.backend.model.Estado;
import com.dev.backend.service.EstadoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estado")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @GetMapping("/")
    public List<Estado> consultar() {
        return estadoService.listAll();
    }

    @PostMapping("/")
    public Estado inserir(@RequestBody Estado estadoEntity) {
        return estadoService.criarEstado(estadoEntity);

    }

    @PutMapping("/")
    public Estado atualizar(@RequestBody Estado estadoEntity) {
        return estadoService.atualizarEstado(estadoEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long estadoId) {
        estadoService.eliminarEstado(estadoId);
        return ResponseEntity.noContent().build();
    }
}
