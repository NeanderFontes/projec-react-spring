package com.dev.backend.service;

import com.dev.backend.model.Produto;
import com.dev.backend.model.ProdutoImagens;
import com.dev.backend.repository.ProdutoImgRepository;
import com.dev.backend.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor(force = true)
public class ProdutoImgService {

    @Autowired
    private ProdutoImgRepository produtoImgRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private final String FILEPATH = "C:\\Users\\Microsoft\\Desktop\\React-JavaSpring\\Projeto\\projec-react-spring\\backend\\imgPath\\";

    public List<ProdutoImagens> listAll() {
        return produtoImgRepository.findAll();
    }

    @Transactional
    public ProdutoImagens criarImg(Long idProduto, MultipartFile criarFileImgModel) {

        Produto idProdutoAtualDaImg = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        // Verifica se já existe uma imagem para este produto
        if (produtoImgRepository.existsByProduto_Id(idProduto)) {
            throw new RuntimeException("Imagem já existe para este produto.");
        }

        ProdutoImagens novaFileImg = new ProdutoImagens();

        try {
            if (!criarFileImgModel.isEmpty()) {
                byte[] bytes = criarFileImgModel.getBytes();

                String nomeImg = idProdutoAtualDaImg.getId() + "-" + idProdutoAtualDaImg.getUuid()
                        + "-" + criarFileImgModel.getOriginalFilename();

                Path caminhoDiretrio = Paths
                        .get(FILEPATH + nomeImg);
                Files.write(caminhoDiretrio, bytes);
                novaFileImg.setNome(nomeImg);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage(), e);
        }

//        novaFileImg.getProduto().setDataCriacao(idProdutoAtualDaImg.getDataCriacao());
//        novaFileImg.getProduto().setDataAtualizacao(idProdutoAtualDaImg.getDataAtualizacao());
//        novaFileImg.getProduto().setCategoria(idProdutoAtualDaImg.getCategoria());
//        novaFileImg.getProduto().setMarca(idProdutoAtualDaImg.getMarca());
//        novaFileImg.getProduto().setCustoProduto(idProdutoAtualDaImg.getCustoProduto());
//        novaFileImg.getProduto().setCustoVenda(idProdutoAtualDaImg.getCustoVenda());
//        novaFileImg.getProduto().setDescricaoCurta(idProdutoAtualDaImg.getDescricaoCurta());
//        novaFileImg.getProduto().setDescricaoDetalhada(idProdutoAtualDaImg.getDescricaoDetalhada());

        novaFileImg.setProduto(idProdutoAtualDaImg);
        novaFileImg.setDataCriacao(new Date());

        return produtoImgRepository.saveAndFlush(novaFileImg);

    }

    public ProdutoImagens atualizarImg(Long idProdutoParaAtt, Long idImgParaAtt, MultipartFile attFileImgModel) {
        Produto idProdutoModelDB = produtoRepository.findById(idProdutoParaAtt)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        Produto uuidModelParaVerificarDB = produtoRepository.findByUuid(idProdutoModelDB.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("UUID da Imagem não encontrado"));

        ProdutoImagens idImgModelDB = produtoImgRepository.findById(idImgParaAtt)
                .orElseThrow(() -> new IllegalArgumentException("Imagem não encontrado"));

        if (!idImgModelDB.getProduto().getId().equals(idProdutoModelDB.getId())) {
            throw new IllegalArgumentException("A imagem não pertence ao produto especificado.");
        }

        try {
            if (!attFileImgModel.isEmpty()) {
                // Deleta o arquivo anterior do sistema de arquivos
                Path caminhoArquivoAnterior = Paths.get(FILEPATH + idImgModelDB.getNome());
                if (Files.exists(caminhoArquivoAnterior)) {
                    Files.delete(caminhoArquivoAnterior);  // Deleta o arquivo se ele existir
                }

                // Salva a nova imagem no sistema de arquivos
                byte[] bytes = attFileImgModel.getBytes();

                String nomeImgAtt = idProdutoModelDB.getId() + "-" + uuidModelParaVerificarDB.getUuid()
                        + "-" + attFileImgModel.getOriginalFilename();

                Path caminhoDiretrio = Paths
                        .get(FILEPATH + nomeImgAtt);
                Files.write(caminhoDiretrio, bytes);

                idImgModelDB.setNome(nomeImgAtt);

                if (idImgModelDB.getDataAtualizacao() != null) {
                    idImgModelDB.getProduto().setDataAtualizacao(idProdutoModelDB.getDataAtualizacao());
                } else {
                    idImgModelDB.setDataAtualizacao(new Date());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao atualizar imagem: " + e.getMessage(), e);
        }

        idImgModelDB.getProduto().setDataCriacao(idProdutoModelDB.getDataCriacao());
        idImgModelDB.getProduto().setDataAtualizacao(idProdutoModelDB.getDataAtualizacao());
        idImgModelDB.getProduto().setCategoria(idProdutoModelDB.getCategoria());
        idImgModelDB.getProduto().setMarca(idProdutoModelDB.getMarca());
        idImgModelDB.getProduto().setCustoProduto(idProdutoModelDB.getCustoProduto());
        idImgModelDB.getProduto().setCustoVenda(idProdutoModelDB.getCustoVenda());
        idImgModelDB.getProduto().setDescricaoCurta(idProdutoModelDB.getDescricaoCurta());
        idImgModelDB.getProduto().setDescricaoDetalhada(idProdutoModelDB.getDescricaoDetalhada());

        return produtoImgRepository.saveAndFlush(idImgModelDB);
    }

    public void eliminar(Long idImgParaEliminar) {
        // Busca a imagem e verifica se ela existe em DB
        ProdutoImagens imagemEmDB = produtoImgRepository.findById(idImgParaEliminar)
                .orElseThrow(() -> new IllegalArgumentException("Imagem com ID " + idImgParaEliminar + " não encontrada"));

        // Deleta o arquivo anterior do sistema de arquivos
        Path caminhoArquivoAnterior = Paths.get(FILEPATH + imagemEmDB.getNome());
        if (Files.exists(caminhoArquivoAnterior)) {
            try {
                Files.delete(caminhoArquivoAnterior);  // Deleta o arquivo se ele existir
            } catch (IOException e) {
                throw new RuntimeException("Erro ao excluir o arquivo de imagem: " + e.getMessage(), e);
            }
        }

        // Eliminando imagem em DB
        produtoImgRepository.deleteById(imagemEmDB.getId());
    }
}
