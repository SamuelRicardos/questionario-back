package com.example.questionarios.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CategoriaDesempenho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    private int acertos;

    private int erros;

    @ManyToOne
    private User user;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString
    @Override
    public String toString() {
        return "CategoriaDesempenho{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                ", acertos=" + acertos +
                ", erros=" + erros +
                ", user=" + (user != null ? user.getId_usuario() : null) +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaDesempenho)) return false;
        CategoriaDesempenho that = (CategoriaDesempenho) o;
        return Objects.equals(id, that.id);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
