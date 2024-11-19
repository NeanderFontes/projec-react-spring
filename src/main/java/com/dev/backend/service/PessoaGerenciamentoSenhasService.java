package com.dev.backend.service;

import com.dev.backend.model.Pessoa;
import com.dev.backend.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PessoaGerenciamentoSenhasService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmailService emailService;

    public String solicitarCodigo(String enviarCodigo) {
        Pessoa pessoa = pessoaRepository.findByEmail(enviarCodigo);
        try {
            if (pessoa.getEmail() != null) {
                pessoa.setCodigoRecuperarSenha(gerarCodigoRecuperarSenha(pessoa.getId()));
                pessoa.setDataEnvioDoCodigo(new Date());
                pessoaRepository.saveAndFlush(pessoa);

                emailService.enviarEmailTextoCliente(pessoa
                        , "Código de Recuperação de senha."
                        , "Olá, o seu código para recuperação de senha: " + pessoa.getCodigoRecuperarSenha());
            }
            return "Código Enviado!";
        } catch (Exception ex) {
            throw new RuntimeException("Erro no envio do código: " + ex.getMessage());
        }
    }

    public String alterarSenha(Pessoa pessoa) {
        // Encontre o registro pela combinação de email e código
        Pessoa pessoaModelDBA = pessoaRepository.findByEmailAndCodigoRecuperarSenha(pessoa.getEmail(), pessoa.getCodigoRecuperarSenha());

        // Verifique se o objeto encontrado não é null
        if (pessoaModelDBA == null) {
            throw new IllegalArgumentException("Email ou código não encontrado!");
        }

        // Verifique se o código está dentro do tempo de validade
        long tempoValidoEmMilissegundos = 900000; // 15 minutos
        long tempoDecorrido = new Date().getTime() - pessoaModelDBA.getDataEnvioDoCodigo().getTime();

        if (tempoDecorrido < tempoValidoEmMilissegundos) {
            // Se o código for válido, altere a senha
            pessoaModelDBA.setSenha(pessoa.getSenha());
            pessoaModelDBA.setCodigoRecuperarSenha(null);  // Limpeza do código de recuperação
            pessoaRepository.saveAndFlush(pessoaModelDBA);
            return "Senha alterada com Sucesso!";
        } else {
            return "Tempo expirado, solicite novo código!";
        }
    }

    private String gerarCodigoRecuperarSenha(Long id) {
        DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssmm");
        return format.format(new Date()) + id;
    }
}
