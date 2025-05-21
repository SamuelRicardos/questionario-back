package com.example.questionarios.services;

import com.example.questionarios.dto.AuthDTO;
import com.example.questionarios.dto.UserDTO;
import com.example.questionarios.infra.TokenService;
import com.example.questionarios.models.User;
import com.example.questionarios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public User register(UserDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User();
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setSenha(passwordEncoder.encode(dto.senha()));

        return userRepository.save(user);
    }

    public String login(AuthDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.senha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return tokenService.generateToken(user);
    }
}
