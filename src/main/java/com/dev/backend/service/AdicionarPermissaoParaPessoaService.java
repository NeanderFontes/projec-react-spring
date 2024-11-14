package com.dev.backend.service;

import com.dev.backend.model.Permissao;
import com.dev.backend.model.PermissaoPessoa;
import com.dev.backend.model.Pessoa;
import com.dev.backend.repository.DadosPermissaoPessoaRepository;
import com.dev.backend.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdicionarPermissaoParaPessoaService {

    @Autowired // Injeta automaticamente o repositório de permissões
    private DadosPermissaoPessoaRepository dadosPermissaoPessoaRepository;

    @Autowired // Injeta automaticamente o repositório de permissões gerais
    private PermissaoRepository permissaoRepository;

    public void adicionarTipoDePermissaoParaPessoaCliente(Pessoa pessoaParaAdicionarPermissao) {
        // Método para buscar uma permissão com o nome "cliente" no banco de dados.
        List<Permissao> listarPermissao = permissaoRepository.findByNome("cliente");

        // Verifica se foi encontrada pelo menos uma permissão "cliente"
        if (listarPermissao.isEmpty()) {
            throw new IllegalArgumentException("Permissão 'cliente' não encontrada no banco de dados.");
        }

        // Obtém a permissão "cliente"
        Permissao permissaoCliente = listarPermissao.get(0);

        // Verifica se a pessoa já possui a permissão "cliente"
        boolean permissaoJaAssociada = dadosPermissaoPessoaRepository
                .existsByPessoaAndPermissao(pessoaParaAdicionarPermissao, permissaoCliente);
        if (permissaoJaAssociada) {
            throw new IllegalArgumentException("A Pessoa " + pessoaParaAdicionarPermissao.getNome()
                    + " já possui a permissão de cliente.");
        }

        // Cria uma nova associação de PermissãoPessoa entre a Pessoa e a Permissão encontrada
        PermissaoPessoa permissaoPessoa = new PermissaoPessoa();
        permissaoPessoa.setPessoa(pessoaParaAdicionarPermissao);
        permissaoPessoa.setPermissao(permissaoCliente);
        permissaoPessoa.setDataCriacao(new Date()); // Define a data de criação para a nova permissão

        // Salva a associação PermissãoPessoa no banco de dados
        dadosPermissaoPessoaRepository.saveAndFlush(permissaoPessoa);
    }

}
