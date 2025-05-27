package com.example.questionarios.dto;

import java.util.List;

public record UserProfileDTO(String nome, String email, List<CategoriaDesempenhoDTO> desempenhos) {}
