package com.registradorSQL.SQLCatalog.dto;

import com.registradorSQL.SQLCatalog.enu.Role;
import com.registradorSQL.SQLCatalog.model.Usuario;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private Role role;
    private LocalDateTime dataCriacao;
    private boolean ativo;

    public UsuarioResponse() {
    }

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.role = usuario.getRole();
        this.dataCriacao = usuario.getDataCriacao();
        this.ativo = usuario.isUsarioAtivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}