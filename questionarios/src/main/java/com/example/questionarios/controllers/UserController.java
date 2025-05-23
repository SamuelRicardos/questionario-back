package com.example.questionarios.controllers;

import com.example.questionarios.dto.AuthDTO;
import com.example.questionarios.dto.NovaSenhaDTO;
import com.example.questionarios.dto.UserDTO;
import com.example.questionarios.models.User;
import com.example.questionarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        User user = userService.register(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/nova-senha")
    public ResponseEntity<String> resetPassword(@RequestBody NovaSenhaDTO request) {
        System.out.println("Token: " + request.token());
        System.out.println("NovaSenha: " + request.novaSenha());

        boolean success = userService.resetPassword(request.token(), request.novaSenha());

        if (success) {
            return ResponseEntity.ok("Senha atualizada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
    }
}
