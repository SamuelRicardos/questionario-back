package com.example.questionarios.dto;

import java.util.List;

public record PerguntaDTO(
        String question,
        List<String> options,
        String correctAnswer
) {}
