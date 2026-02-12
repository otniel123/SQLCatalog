package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.dto.EstatisticasResponse;
import com.registradorSQL.SQLCatalog.dto.UsuarioResponse;
import com.registradorSQL.SQLCatalog.enu.Role;
import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.model.Usuario;
import com.registradorSQL.SQLCatalog.repository.ScriptRepository;
import com.registradorSQL.SQLCatalog.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ScriptRepository scriptRepository;

    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .collect(Collectors.toList());
    }

    public UsuarioResponse buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse alterarRole(Long id, Role novaRole) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setRole(novaRole);
        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setAtivo('N');
        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario);
    }

    public UsuarioResponse ativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setAtivo('S');
        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario);
    }

    public List<Script> listarTodosScripts() {
        return scriptRepository.findAll();
    }

    public List<Script> listarScriptsPorUsuario(Long usuarioId) {
        return scriptRepository.findByUsuarioId(usuarioId);
    }

    public EstatisticasResponse getEstatisticas() {
        long totalUsuarios = usuarioRepository.count();
        long usuariosAtivos = usuarioRepository.countByAtivo(true);
        long totalScripts = scriptRepository.count();

        Map<String, Long> scriptsPorBanco = scriptRepository.findAll()
                .stream()
                .filter(script -> script.getBancoDados() != null)
                .collect(Collectors.groupingBy(
                        script -> script.getBancoDados().name(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Map<String, Long> scriptsPorCategoria = scriptRepository.findAll()
                .stream()
                .filter(script -> script.getCategoria() != null)
                .collect(Collectors.groupingBy(
                        script -> script.getCategoria().name(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return new EstatisticasResponse(
                totalUsuarios,
                usuariosAtivos,
                totalScripts,
                scriptsPorBanco,
                scriptsPorCategoria
        );
    }
}