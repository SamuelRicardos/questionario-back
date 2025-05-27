package com.example.questionarios.dto;

import java.util.List;

public record PerguntaDTO(
        String questao,
        List<String> opcoes,
        String questaoCorreta,
        String explicacao
) {}
