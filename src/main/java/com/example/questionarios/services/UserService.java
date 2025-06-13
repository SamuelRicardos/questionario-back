package com.example.questionarios.services;

import com.example.questionarios.dto.*;
import com.example.questionarios.infra.TokenService;
import com.example.questionarios.models.User;
import com.example.questionarios.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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

    public LoginResponseDTO login(AuthDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.senha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(token, user.getEmail());
    }

    public boolean resetPassword(String token, String newPassword) {
        String userEmail = tokenService.validateToken(token);

        if (userEmail == null) {
            return false;
        }

        var userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.setSenha(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    public UserProfileDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var desempenhos = user.getDesempenhos().stream()
                .map(d -> new CategoriaDesempenhoDTO(d.getCategoria(), d.getAcertos(), d.getErros()))
                .collect(Collectors.toList());

        return new UserProfileDTO(user.getNome(), user.getEmail(), desempenhos);
    }
}
