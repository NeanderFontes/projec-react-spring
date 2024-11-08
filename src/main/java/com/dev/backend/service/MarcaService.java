package com.dev.backend.service;

import com.dev.backend.model.Marca;
import com.dev.backend.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> listAll() {
        return marcaRepository.findAll();
    }

    public Marca criarMarca(Marca novoModelMarca) {
        novoModelMarca.setDataCriacao(new Date());
        return marcaRepository.saveAndFlush(novoModelMarca);
    }

    public Marca atualizarMarca(Marca atualizarModelMarca) {
        atualizarModelMarca.setDataAtualizacao(new Date());
        return marcaRepository.saveAndFlush(atualizarModelMarca);
    }

    public void eliminarMarca(Long eliminarMarcaPorId) {
        Marca eliminarModel = marcaRepository.findById(eliminarMarcaPorId).get();
        marcaRepository.deleteById(eliminarModel.getId());
    }
}
