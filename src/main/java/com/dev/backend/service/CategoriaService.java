package com.dev.backend.service;

import com.dev.backend.model.Categoria;
import com.dev.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listAll() {

        return categoriaRepository.findAll();
    }

    public Categoria criarCategoria(Categoria criarCategoriaModel) {

        criarCategoriaModel.setDataCriacao(new Date());

        return categoriaRepository.saveAndFlush(criarCategoriaModel);
    }

    public Categoria atualizarCategoria(Categoria atualizarCategoriaModel) {

        Date dataCriacao = atualizarCategoriaModel.getDataCriacao();
        atualizarCategoriaModel.setDataCriacao(dataCriacao);
        atualizarCategoriaModel.setDataAtualizacao(new Date());

        return categoriaRepository.save(atualizarCategoriaModel);
    }

    public void eliminarCategoria(Long eliminarCategoriaPorId) {
        Categoria eliminarCategoriaDB = categoriaRepository.findById(eliminarCategoriaPorId).get();
        categoriaRepository.deleteById(eliminarCategoriaDB.getId());
    }
}
