package com.example.questionarios.repositories;

import com.example.questionarios.models.CategoriaDesempenho;
import com.example.questionarios.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaDesempenhoRepository extends JpaRepository<CategoriaDesempenho, Long> {
    Optional<CategoriaDesempenho> findByUserAndCategoria(User user, String categoria);
}