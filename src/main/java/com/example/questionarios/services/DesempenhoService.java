package com.example.questionarios.services;

import com.example.questionarios.models.CategoriaDesempenho;
import com.example.questionarios.models.User;
import com.example.questionarios.repositories.CategoriaDesempenhoRepository;
import com.example.questionarios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesempenhoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoriaDesempenhoRepository desempenhoRepository;

    public void atualizarDesempenho(String email, String categoria, boolean acertou) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        CategoriaDesempenho desempenho = desempenhoRepository.findByUserAndCategoria(user, categoria)
                .orElse(new CategoriaDesempenho());

        desempenho.setUser(user);
        desempenho.setCategoria(categoria);

        if (acertou) {
            desempenho.setAcertos(desempenho.getAcertos() + 1);
        } else {
            desempenho.setErros(desempenho.getErros() + 1);
        }

        desempenhoRepository.save(desempenho);
    }
}
