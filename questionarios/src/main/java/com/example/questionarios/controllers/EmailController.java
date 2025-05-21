package com.example.questionarios.controllers;

import com.example.questionarios.dto.EmailRequest;
import com.example.questionarios.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailClientService) {
        this.emailService = emailClientService;
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> enviarEmailRedefinicao(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String token = UUID.randomUUID().toString();

        try {
            emailService.enviarEmailRedefinicao(email, token);
            return ResponseEntity.ok("Email enviado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao enviar o e-mail.");
        }
    }
}
