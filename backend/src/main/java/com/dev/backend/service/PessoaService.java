package com.dev.backend.service;

import com.dev.backend.model.Pessoa;
import com.dev.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa criarPessoa(Pessoa criarPessoaModel) {
        criarPessoaModel.setDataCriacao(new Date());
        return pessoaRepository.saveAndFlush(criarPessoaModel);
    }

    public Pessoa atualizarPessoa(Pessoa attPessoaModel) {
        attPessoaModel.setDataAtualiacao(new Date());
        return pessoaRepository.saveAndFlush(attPessoaModel);
    }

    public void eliminarPessoa(Long idEliminarPessoaModel) {
        Pessoa pessoaParaEliminar = pessoaRepository.findById(idEliminarPessoaModel).get();
        pessoaRepository.deleteById(pessoaParaEliminar.getId());
    }

}
