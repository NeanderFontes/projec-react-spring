package com.dev.backend.service;

import com.dev.backend.model.Categoria;
import com.dev.backend.model.Marca;
import com.dev.backend.model.Produto;
import com.dev.backend.repository.CategoriaRepository;
import com.dev.backend.repository.MarcaRepository;
import com.dev.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }

    public Produto criarProduto(Produto criarNovoProdutoModel) {

        // Validações para criar Produto
        validarProdutoIdMarca(criarNovoProdutoModel);
        validarProdutoIdCategoria(criarNovoProdutoModel);
        validarProdutoUUID(criarNovoProdutoModel);

        // Setar Valores da Marca existentes
        setarValoresMarcaEmProduto(criarNovoProdutoModel);

        // Setar Valores da Categoria existentes
        setarValoresCategoriaEmProduto(criarNovoProdutoModel);

        // Criar Data para o novo Produto
        criarNovoProdutoModel.setDataCriacao(new Date());

        return produtoRepository.saveAndFlush(criarNovoProdutoModel);
    }

    public Produto atualizarProduto(Produto atualizarProdutoModel) {

        // Buscar id produto e validar para Atuaizar
        Produto produto = produtoRepository.findById(atualizarProdutoModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Id " + atualizarProdutoModel.getId() + " inválido."));

        // Validações para criar Produto
        validarProdutoIdMarca(atualizarProdutoModel);
        validarProdutoIdCategoria(atualizarProdutoModel);
        validarDadosAtualizarProduto(atualizarProdutoModel);

        // Criar atualização da Data para o Produto modificado
        Date dataAtual = produto.getDataCriacao();
        atualizarProdutoModel.setDataCriacao(dataAtual);
        atualizarProdutoModel.setDataAtualizacao(new Date());

        return produtoRepository.saveAndFlush(atualizarProdutoModel);
    }

    public void elminarProduto(Long produtoIdModel) {
        Produto produtoParaEliminar = produtoRepository.findById(produtoIdModel).get();
        produtoRepository.deleteById(produtoParaEliminar.getId());
    }

    private Produto validarProdutoIdMarca(Produto produtoIdMarca) {
        // Verifica se a Marca associada ao produto existe
        Marca idMarcaAtualDoProduto = marcaRepository.findById(produtoIdMarca.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca " + produtoIdMarca.getMarca().getNome()
                        + " do Produto" + produtoIdMarca.getDescricaoCurta() + " não encontrada!"));

        // Verifica se já existe um produto com o mesmo nome ou código para a mesma marca
        if (produtoRepository.existsByDescricaoCurtaAndMarcaId(produtoIdMarca.getDescricaoCurta(), idMarcaAtualDoProduto.getId())) {
            throw new RuntimeException("Já existe um Produto " + produtoIdMarca.getDescricaoCurta()
                    + " com este nome registrado para a mesma marca.");
        }

        // Associa a Marca encontrada ao produto
        produtoIdMarca.setMarca(idMarcaAtualDoProduto);

        return produtoIdMarca;
    }

    private Produto validarProdutoIdCategoria(Produto produtoIdCategoria) {
        // Verifica se a Categoria associada ao produto existe
        Categoria idCategoriaAtualDoProduto = categoriaRepository.findById(produtoIdCategoria.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria " + produtoIdCategoria.getCategoria().getNome()
                        + " do Produto " + produtoIdCategoria.getDescricaoCurta() + " não encontrado."));

        // Verifica se já existe um produto com o mesmo nome ou código para a mesma marca
        if (produtoRepository.existsByDescricaoCurtaAndCategoriaId(produtoIdCategoria.getDescricaoCurta(),
                idCategoriaAtualDoProduto.getId())) {
            throw new RuntimeException("Já existe um Produto " + produtoIdCategoria.getDescricaoCurta()
                    + " com este nome registrado para a mesma categoria.");
        }

        // Associa a Categoria encontrada ao produto
        produtoIdCategoria.setCategoria(idCategoriaAtualDoProduto);

        return produtoIdCategoria;
    }

    private Produto validarProdutoUUID(Produto validarProdutoUUID) {
        // Verificar se o produto já existe pelo ID
//        Produto produtoExistenteEmDB = produtoRepository.findById(validarProdutoUUID.getId())
//                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + validarProdutoUUID.getId() + " não encontrado."));

        // Verificar se a categoria e a marca associadas ao produto existem
        categoriaRepository.findById(validarProdutoUUID.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria com ID " + validarProdutoUUID.getCategoria().getId() + " não encontrada."));

        marcaRepository.findById(validarProdutoUUID.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca com ID " + validarProdutoUUID.getMarca().getId() + " não encontrada."));

        /* Verificar se o produto já possui um UUID associado
        Todo Criar uma classe que seja Chave unica entre idMarca e idCategoria que contenha as informações
        if (idCategria != null && idMarca != null) {
             de produtos cadastrados que sejam da mesma categoria e marca com o UUID de relacionamento
            var uuidProdutoCadastrado = produtoRepository.findByUuid()
        }
        */

        if (validarProdutoUUID.getUuid() != null) {
            throw new RuntimeException("Já existe um UUID já associado a este Produto"
                    + validarProdutoUUID.getDataCriacao());
        } //else {
//            // Verifica se o UUID do produto já está presente
//            Produto verificarUUID = produtoRepository.findByUuid(validarProdutoUUID.getUuid())
//                    .orElseThrow(() -> new IllegalArgumentException("UUID " + validarProdutoUUID.getUuid()
//                            + " do Produto " + validarProdutoUUID.getDescricaoCurta() + " não encontrado."));
//        }

        // Associa um novo UUID ao Novo Produto
        validarProdutoUUID.setUuid(UUID.randomUUID());

        return validarProdutoUUID;
    }

    private void validarDadosAtualizarProduto(Produto atualizarProdutoModel) {
        // Verificar se o produto já existe pelo ID
        Produto produtoExistenteEmDB = produtoRepository.findById(atualizarProdutoModel.getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + atualizarProdutoModel.getId() + " não encontrado."));

        // Associa um novo UUID ao Novo Produto
        if (atualizarProdutoModel.getId() == null) {
            atualizarProdutoModel.setUuid(UUID.randomUUID());
        } else {
            atualizarProdutoModel.setUuid(produtoExistenteEmDB.getUuid());

            // Atribui o Custo do Produto existente
            if (atualizarProdutoModel.getCustoProduto() == null) {
                atualizarProdutoModel.setCustoProduto(produtoExistenteEmDB.getCustoProduto());
            }

            // Atribui o custo de Venda do Produto existente
            if (atualizarProdutoModel.getCustoVenda() == null) {
                atualizarProdutoModel.setCustoVenda(produtoExistenteEmDB.getCustoVenda());
            }

            // Atribui a Descricao Curta do Produto existente
            if (atualizarProdutoModel.getDescricaoCurta() == null) {
                atualizarProdutoModel.setDescricaoCurta(produtoExistenteEmDB.getDescricaoCurta());
            }

            // Atribui a Descricao Detalhada do Produto existente
            if (atualizarProdutoModel.getDescricaoDetalhada() == null) {
                atualizarProdutoModel.setDescricaoDetalhada(produtoExistenteEmDB.getDescricaoDetalhada());
            }
        }
    }

    private void setarValoresMarcaEmProduto(Produto criarNovoProdutoModel) {
        Marca marcaDoProtudo = marcaRepository.findById(criarNovoProdutoModel.getMarca().getId()).get();
        criarNovoProdutoModel.getMarca().setDataCriacao(marcaDoProtudo.getDataCriacao());
        criarNovoProdutoModel.getMarca().setDataAtualizacao(marcaDoProtudo.getDataAtualizacao());
        criarNovoProdutoModel.getMarca().setNome(marcaDoProtudo.getNome());
    }


    private void setarValoresCategoriaEmProduto(Produto criarNovoProdutoModel) {
        Categoria categoriaDoProtudo = categoriaRepository.findById(criarNovoProdutoModel.getCategoria().getId()).get();
        criarNovoProdutoModel.getCategoria().setDataCriacao(categoriaDoProtudo.getDataCriacao());
        criarNovoProdutoModel.getCategoria().setDataAtualizacao(categoriaDoProtudo.getDataAtualizacao());
        criarNovoProdutoModel.getCategoria().setNome(categoriaDoProtudo.getNome());
    }
}
