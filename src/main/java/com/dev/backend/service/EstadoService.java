package com.dev.backend.service;

import com.dev.backend.model.Estado;
import com.dev.backend.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> listAll() {
        return estadoRepository.findAll();
    }

    public Estado criarEstado(Estado estadoEntity) {
        estadoEntity.setDataCriacao(new Date());
        return estadoRepository.saveAndFlush(estadoEntity);
    }

    public Estado atualizarEstado(Estado estadoEntity) {
        estadoEntity.setDataCriacao(estadoEntity.getDataCriacao());
        estadoEntity.setDataAtualizacao(new Date());
        return estadoRepository.saveAndFlush(estadoEntity);

    }

    public void eliminarEstado(Long estadoId) {
        System.out.println(estadoId);
        Estado estadoEliminar = estadoRepository.findById(estadoId).get();
        System.out.println(estadoId);
        estadoRepository.deleteById(estadoEliminar.getId());
    }
}
