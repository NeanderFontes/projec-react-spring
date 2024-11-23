package com.dev.backend.service;

import com.dev.backend.model.Permissao;
import com.dev.backend.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> listAll() {
        return permissaoRepository.findAll();
    }

    public Permissao crarPermissao(Permissao criarPermissaoModel) {
        criarPermissaoModel.setDataCriacao(new Date());
        return permissaoRepository.saveAndFlush(criarPermissaoModel);
    }

    public Permissao attPermisao(Permissao attPermissaoModel) {
        attPermissaoModel.setDataAtualizacao(new Date());
        return permissaoRepository.saveAndFlush(attPermissaoModel);
    }

    public void eliminarPermisao(Long idPermissaoEliminar) {
        Permissao permissaoEliminar = permissaoRepository.findById(idPermissaoEliminar).get();
        permissaoRepository.deleteById(permissaoEliminar.getId());
    }
}
