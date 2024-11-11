package com.dev.backend.service;

import com.dev.backend.model.Cidade;
import com.dev.backend.model.Estado;
import com.dev.backend.repository.CidadeRepository;
import com.dev.backend.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> listAll() {
        return cidadeRepository.findAll();
    }

    public Cidade criarCidade(Cidade novoCidadeModel) {
        boolean nomeNovaCidadeExisteEmDB = cidadeRepository.existsByNome(novoCidadeModel.getNome().trim());

        // Verifica se já existe uma cidade com o mesmo nome
        if (nomeNovaCidadeExisteEmDB) {
            throw new IllegalArgumentException("Uma cidade com este nome já existe.");
        }

        // Código a ser executado se o nome da nova cidade for igual ao nome da cidade no banco
        Estado estadoAtualDaCidadeDB = estadoRepository.findById(novoCidadeModel.getEstado().getId())
                .orElseThrow(() -> new IllegalArgumentException("Estado não encontrado."));

        String siglaAtualEstado = estadoAtualDaCidadeDB.getSigla();
        String nomeEstadoAtuaDB = estadoAtualDaCidadeDB.getNome();
        Date dataCriacaoEstadoAtual = estadoAtualDaCidadeDB.getDataCriacao();
        Date dataAtualizacaoEstadoAtual = estadoAtualDaCidadeDB.getDataAtualizacao();

        // SetSiglaEstado para o que já existe
        if (!siglaAtualEstado.equalsIgnoreCase(novoCidadeModel.getEstado().getSigla())) {
            novoCidadeModel.getEstado().setSigla(siglaAtualEstado.trim().toUpperCase());
        }

        // SetNomeEstado para o que ja existe
        if (!nomeEstadoAtuaDB.equalsIgnoreCase(novoCidadeModel.getEstado().getNome())) {
            novoCidadeModel.getEstado().setNome(nomeEstadoAtuaDB);
        }

        //SetDataCriacao para o que ja existe
        if (!dataCriacaoEstadoAtual.equals(novoCidadeModel.getEstado().getDataCriacao())) {
            novoCidadeModel.getEstado().setDataCriacao(dataCriacaoEstadoAtual);
        }

        if (dataAtualizacaoEstadoAtual != null) {
            novoCidadeModel.getEstado().setDataAtualizacao(dataAtualizacaoEstadoAtual);
        } else {
            novoCidadeModel.setDataCriacao(new Date());
        }

        // Atualiza a Cidade de acordo com a FK do Estado
        novoCidadeModel.setEstado(estadoAtualDaCidadeDB);

        // Cria nova data para a Cidade
        novoCidadeModel.setDataCriacao(new Date());

        return cidadeRepository.saveAndFlush(novoCidadeModel);
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
