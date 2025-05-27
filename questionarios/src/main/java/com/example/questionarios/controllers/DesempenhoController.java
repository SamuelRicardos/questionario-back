package com.example.questionarios.controllers;

import com.example.questionarios.dto.AtualizarDesempenhoDTO;
import com.example.questionarios.services.DesempenhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/desempenho")
public class DesempenhoController {

    @Autowired
    private DesempenhoService desempenhoService;

    @PostMapping("/atualizar")
    public ResponseEntity<Void> atualizarDesempenho(@RequestBody AtualizarDesempenhoDTO dto) {
        desempenhoService.atualizarDesempenho(dto.email(), dto.categoria(), dto.acertou());
        return ResponseEntity.ok().build();
    }
}
