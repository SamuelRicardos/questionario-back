package com.example.questionarios.controllers;

import com.example.questionarios.dto.PerguntaDTO;
import com.example.questionarios.services.GeminiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perguntas")
public class PerguntaController {
    private final GeminiService geminiService;
    private static final Logger logger = LoggerFactory.getLogger(PerguntaController.class);
    public PerguntaController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/gerar")
    public ResponseEntity<PerguntaDTO> gerarPergunta(
            @RequestParam String linguagem,
            @RequestParam String topico
    ) {
        try {
            PerguntaDTO pergunta = geminiService.gerarPergunta(linguagem, topico);
            return ResponseEntity.ok(pergunta);
        } catch (Exception e) {
            logger.error("Erro ao gerar pergunta", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/gerar-geral")
    public ResponseEntity<PerguntaDTO> gerarPerguntaGeral(@RequestParam String topico) {
        try {
            PerguntaDTO pergunta = geminiService.gerarPerguntaGeral(topico);
            return ResponseEntity.ok(pergunta);
        } catch (Exception e) {
            logger.error("Erro ao gerar pergunta geral", e);
            return ResponseEntity.status(500).build();
        }
    }
}
