package com.example.questionarios.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailRedefinicao(String destinatario, String token) {
        String linkRedefinicao = "http://localhost:5173/nova-senha?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Redefinição de Senha");
        mensagem.setText("Clique no link para redefinir sua senha: " + linkRedefinicao);

        mailSender.send(mensagem);
    }
}
