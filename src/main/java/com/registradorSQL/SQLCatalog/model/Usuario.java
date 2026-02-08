package com.registradorSQL.SQLCatalog.model;

import com.registradorSQL.SQLCatalog.enu.Role;
import jakarta.persistence.*;
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
    private String email;

    @NotBlank
    @NotNull(message = "Senha para o seu usuário é obrigatório")
    private String password;

    @NotBlank
    @NotNull
    private Role role = Role.USER;

    @NotBlank
    @NotNull
    private LocalDateTime dataCriacao;

    private Character ativo;

    public boolean isUsarioAtivo(){
        return this.ativo.equals('S');
    }
}
