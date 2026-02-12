package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.dto.EstatisticasResponse;
import com.registradorSQL.SQLCatalog.dto.UsuarioResponse;
import com.registradorSQL.SQLCatalog.enu.Role;
import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(adminService.listarTodosUsuarios());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.buscarUsuarioPorId(id));
    }

    @PatchMapping("/usuarios/{id}/role")
    public ResponseEntity<UsuarioResponse> alterarRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        return ResponseEntity.ok(adminService.alterarRole(id, role));
    }

    @PatchMapping("/usuarios/{id}/desativar")
    public ResponseEntity<UsuarioResponse> desativarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.desativarUsuario(id));
    }

    @PatchMapping("/usuarios/{id}/ativar")
    public ResponseEntity<UsuarioResponse> ativarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.ativarUsuario(id));
    }

    @GetMapping("/scripts")
    public ResponseEntity<List<Script>> listarTodosScripts() {
        return ResponseEntity.ok(adminService.listarTodosScripts());
    }

    @GetMapping("/usuarios/{id}/scripts")
    public ResponseEntity<List<Script>> listarScriptsDoUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.listarScriptsPorUsuario(id));
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasResponse> getEstatisticas() {
        return ResponseEntity.ok(adminService.getEstatisticas());
    }
}