package com.registradorSQL.SQLCatalog.model;

import com.registradorSQL.SQLCatalog.enu.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull(message = "Nome de usuário é obrigatório")
    private String nome;

    @Column(unique = true)
    @NotBlank
    @NotNull(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotBlank
    @NotNull(message = "Senha para o seu usuário é obrigatório")
    private String password;


    private Role role;

    private LocalDateTime dataCriacao;

    private Character ativo;

    public Usuario() {
    }

    public Usuario(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.dataCriacao = LocalDateTime.now();
        this.ativo = 'S';
    }

    public boolean isUsarioAtivo(){
        return this.ativo.equals('S');
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @NotNull(message = "Nome de usuário é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank @NotNull(message = "Nome de usuário é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotBlank @NotNull(message = "E-mail é obrigatório") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @NotNull(message = "E-mail é obrigatório") String email) {
        this.email = email;
    }

    public @NotBlank @NotNull(message = "Senha para o seu usuário é obrigatório") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @NotNull(message = "Senha para o seu usuário é obrigatório") String password) {
        this.password = password;
    }

    public @NotBlank @NotNull Role getRole() {
        return role;
    }

    public void setRole(@NotBlank @NotNull Role role) {
        this.role = role;
    }

    public @NotBlank @NotNull LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(@NotBlank @NotNull LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Character getAtivo() {
        return ativo;
    }

    public void setAtivo(Character ativo) {
        this.ativo = ativo;
    }
}
