package com.dev.backend.service;

import com.dev.backend.enums.StatusEmailEnum;
import com.dev.backend.model.EmailModel;
import com.dev.backend.model.Pessoa;
import com.dev.backend.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailModel enviarEmailTextoCliente(Pessoa pessoaDestinatario, String tituloEmail, String conteudoMsg) {
        // Nova instancia para criar email
        EmailModel emailModel = new EmailModel();
        emailModel.setDataEnvioEmail(new Date());
        emailModel.setReferenciaDono(pessoaDestinatario.getId().toString()); // Id do usuário que for utilizar do microservice

        try {
            // Nova instancia para criar Objeto Email
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            // Set em remetente do Email
            simpleMailMessage.setFrom(remetente);
            emailModel.setEmailRemetente(remetente);

            // Set em destinatario do Email
            simpleMailMessage.setTo(pessoaDestinatario.getEmail());
            emailModel.setEmailDestinatario(pessoaDestinatario.getEmail());

            // Set em Asunto ou Titulo do Email
            simpleMailMessage.setSubject(tituloEmail);
            emailModel.setAssuntoDoEmail(tituloEmail);

            // Set em Conteúdo do Email
            simpleMailMessage.setText(conteudoMsg);
            emailModel.setCorpoTextoDoEmail(conteudoMsg);

            // Realizar o Envio com os complementos acima para o Email
            javaMailSender.send(simpleMailMessage);

            emailModel.setStatusEmail(StatusEmailEnum.SENT);
        } catch (MailException ex) {
            emailModel.setStatusEmail(StatusEmailEnum.ERROR);
            // Logando a exceção com detalhes
            System.err.println("Erro ao enviar e-mail: " + ex.getMessage());
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
