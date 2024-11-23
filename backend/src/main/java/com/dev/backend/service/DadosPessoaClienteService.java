package com.dev.backend.service;

import com.dev.backend.DTO.requestDTO.DadosPessoaClienteCadastroRequestDTO;
import com.dev.backend.model.Pessoa;
import com.dev.backend.repository.DadosPessoaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

// Define a classe como um serviço gerenciado pelo Spring
@Service
public class DadosPessoaClienteService {

    @Autowired // Injeta automaticamente o repositório para acesso ao banco de dados
    private DadosPessoaClienteRepository dadosPessoaClienteRepository;

    @Autowired // Injeta automaticamente o serviço de adicionar permissões
    private AdicionarPermissaoParaPessoaService adicionarPermissaoParaPessoaService;

    @Autowired
    private EmailService emailService;

    public Pessoa registarDadoPessoaCliente(DadosPessoaClienteCadastroRequestDTO novoRegistoPessoaDTO) {
        try {
            // Converte o objeto DTO recebido em um modelo de entidade Pessoa
            Pessoa novoRegistoPessoaModel = novoRegistoPessoaDTO.converterDTOParaModel(novoRegistoPessoaDTO);
            // Define a data de criação para o registro
            novoRegistoPessoaModel.setDataCriacao(new Date());

            // Nova instancia para salvar Pessoa
            Pessoa novoObjetoPessoa = dadosPessoaClienteRepository.saveAndFlush(novoRegistoPessoaModel);
            // Adiciona a permissão de "cliente" para o novo registro de Pessoa
            adicionarPermissaoParaPessoaService.adicionarTipoDePermissaoParaPessoaCliente(novoObjetoPessoa);

            emailService.enviarEmailTextoCliente(novoObjetoPessoa, "Cadastro Concluído", "Bem-vindo!");

            // Salva o registro da nova Pessoa no banco de dados e retorna o objeto salvo
            return novoObjetoPessoa;
        } catch (Exception ex) {
            System.err.println("Erro ao registrar cliente: " + ex.getMessage());
            throw new RuntimeException("Erro no registro de cliente: " + ex.getMessage());
        }
    }
}
