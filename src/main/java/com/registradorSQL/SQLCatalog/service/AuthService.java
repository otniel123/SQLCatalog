package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.dto.LoginRequest;
import com.registradorSQL.SQLCatalog.dto.LoginResponse;
import com.registradorSQL.SQLCatalog.dto.RegistroRequest;
import com.registradorSQL.SQLCatalog.dto.UsuarioResponse;
import com.registradorSQL.SQLCatalog.enu.Role;
import com.registradorSQL.SQLCatalog.exception.CredenciaisInvalidasException;
import com.registradorSQL.SQLCatalog.exception.EmailJaCadastradoException;
import com.registradorSQL.SQLCatalog.model.Usuario;
import com.registradorSQL.SQLCatalog.repository.UsuarioRepository;
import com.registradorSQL.SQLCatalog.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Value("${jwt.expiration}")
    private Long expiration;

    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Role.USER);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setAtivo('S');

        return usuarioRepository.save(usuario);
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CredenciaisInvalidasException("Credenciais inválidas"));

        if (!usuario.isUsarioAtivo()) {
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        if (!passwordEncoder.matches(request.getSenha(), usuario.getPassword())) {
            throw new CredenciaisInvalidasException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(
                token,
                expiration / 1000,
                new UsuarioResponse(usuario)
        );
    }

    public Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("Usuário não encontrado"));
    }
}