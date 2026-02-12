package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.dto.LoginRequest;
import com.registradorSQL.SQLCatalog.dto.LoginResponse;
import com.registradorSQL.SQLCatalog.dto.RegistroRequest;
import com.registradorSQL.SQLCatalog.dto.UsuarioResponse;
import com.registradorSQL.SQLCatalog.model.Usuario;
import com.registradorSQL.SQLCatalog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        Usuario usuario = authService.registrar(request);
        return ResponseEntity.status(201).body(new UsuarioResponse(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}