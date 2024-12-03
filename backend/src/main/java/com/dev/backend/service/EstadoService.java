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
        estadoEntity.getNome().trim();
        estadoEntity.getSigla().trim();
        estadoEntity.getSigla().toUpperCase();
        estadoEntity.setDataCriacao(new Date());
        return estadoRepository.saveAndFlush(estadoEntity);
    }

    public Estado atualizarEstado(Estado estadoEntity) {
        Estado estadoAtual = estadoRepository.findById(estadoEntity.getId()).get();
        Date dataCriacaoAtual = estadoAtual.getDataCriacao();

        estadoEntity.setDataCriacao(dataCriacaoAtual);
        estadoEntity.setDataAtualizacao(new Date());

        return estadoRepository.saveAndFlush(estadoEntity);

    }

    public void eliminarEstado(Long estadoId) {
        Estado estadoEliminar = estadoRepository.findById(estadoId).get();
        estadoRepository.deleteById(estadoEliminar.getId());
    }
}
