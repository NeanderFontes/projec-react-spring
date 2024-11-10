package com.dev.backend.service;

import com.dev.backend.model.Cidade;
import com.dev.backend.model.Estado;
import com.dev.backend.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> listAll() {
        return cidadeRepository.findAll();
    }

    public Cidade criarCidade(Cidade novoModelCidade) {
        // Todo Atualizar depois referencias e validações respectivas
//        String nomeEstadoUpperCase =  novoModelCidade.getEstado().getNome().trim().toUpperCase();
//        novoModelCidade.getEstado().setNome(nomeEstadoUpperCase);
        novoModelCidade.setDataCriacao(new Date());
        return cidadeRepository.saveAndFlush(novoModelCidade);
    }

    public Cidade atualizarCidade(Cidade atualizarModelCidade) {
        atualizarModelCidade.setDataAtualizacao(new Date());
        return cidadeRepository.saveAndFlush(atualizarModelCidade);
    }

    public void eliminarCidaide(Long eliminarCidadePorId) {
        Cidade eliminarModelDBA = cidadeRepository.findById(eliminarCidadePorId).get();
        cidadeRepository.deleteById(eliminarModelDBA.getId());
    }
}
