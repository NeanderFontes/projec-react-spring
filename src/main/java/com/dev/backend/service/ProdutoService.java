package com.dev.backend.service;

import com.dev.backend.model.Produto;
import com.dev.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto criarNovoProdutoModel) {
        // Verifica se o UUID do produto já está presente
        if (criarNovoProdutoModel.getUuid() != null) {
            throw new RuntimeException("UUID já associado a uma imagem");
        }
        criarNovoProdutoModel.setUuid(UUID.randomUUID());
        criarNovoProdutoModel.setDataCriacao(new Date());
        return produtoRepository.saveAndFlush(criarNovoProdutoModel);
    }

    public Produto atualizarProduto(Produto atualizarProdutoModel) {
        Produto produto = produtoRepository.findById(atualizarProdutoModel.getId()).get();
        Date dataAtual = produto.getDataCriacao();

        atualizarProdutoModel.setDataCriacao(dataAtual);
        atualizarProdutoModel.setDataAtualizacao(new Date());

        return produtoRepository.saveAndFlush(atualizarProdutoModel);
    }

    public void elminarProduto(Long produtoIdModel) {
        Produto produtoParaEliminar = produtoRepository.findById(produtoIdModel).get();
        produtoRepository.deleteById(produtoParaEliminar.getId());
    }
}
