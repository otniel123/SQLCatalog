package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import com.registradorSQL.SQLCatalog.exception.AcessoNegadoException;
import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.model.Usuario;
import com.registradorSQL.SQLCatalog.repository.ScriptRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public Script criaScript(Script script) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        script.setUsuario(usuarioLogado);
        script.setDataCriacao(LocalDateTime.now());
        script.setDataAtualizacao(LocalDateTime.now());

        return scriptRepository.save(script);
    }

    public List<Script> listarScript(String banco, String categoria, String texto, String tag,
                                     Integer page, Integer size) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        Stream<Script> stream = scriptRepository.findByUsuarioId(usuarioLogado.getId()).stream();

        if (banco != null && !banco.isEmpty()) {
            try {
                BancoDados bancoDados = BancoDados.valueOf(banco.toUpperCase());
                stream = stream.filter(s -> s.getBancoDados() == bancoDados);
            } catch (IllegalArgumentException e) {
                return Collections.emptyList();
            }
        }

        if (categoria != null && !categoria.isEmpty()) {
            try {
                Categoria catg = Categoria.valueOf(categoria.toUpperCase());
                stream = stream.filter(s -> s.getCategoria() == catg);
            } catch (IllegalArgumentException e) {
                return Collections.emptyList();
            }
        }

        if (texto != null && !texto.isEmpty()) {
            stream = stream.filter(script ->
                    (script.getDescricao() != null && script.getDescricao().toUpperCase().contains(texto.toUpperCase())) ||
                            (script.getConteudo() != null && script.getConteudo().toUpperCase().contains(texto.toUpperCase())) ||
                            (script.getTitulo() != null && script.getTitulo().toUpperCase().contains(texto.toUpperCase()))
            );
        }

        if (tag != null && !tag.isEmpty()) {
            stream = stream.filter(script ->
                    script.getTags().stream().anyMatch(t -> t.equalsIgnoreCase(tag))
            );
        }

        return stream
                .sorted(Comparator.comparing(Script::getDataAtualizacao,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public Script listarScriptById(Long id) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        Script script = scriptRepository.findById(id)
                .orElse(null);

        if (script == null) {
            return null;
        }

        if (!script.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new AcessoNegadoException("Acesso negado a este script");
        }

        return script;
    }

    @Transactional
    public Script atualizarScript(Script scriptNovo, Long id) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        Optional<Script> optionalScript = scriptRepository.findById(id);

        if (optionalScript.isEmpty()) {
            return null;
        }

        Script scriptOriginal = optionalScript.get();

        if (!scriptOriginal.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new AcessoNegadoException("Acesso negado a este script");
        }

        scriptNovo.setId(id);
        scriptNovo.setUsuario(usuarioLogado);
        scriptNovo.setDataCriacao(scriptOriginal.getDataCriacao());
        scriptNovo.setDataAtualizacao(LocalDateTime.now());

        return scriptRepository.save(scriptNovo);
    }

    @Transactional
    public Integer deleteScript(Long id) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        Optional<Script> optionalScript = scriptRepository.findById(id);

        if (optionalScript.isEmpty()) {
            return 1;
        }

        Script script = optionalScript.get();

        if (!script.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new AcessoNegadoException("Acesso negado a este script");
        }

        scriptRepository.deleteById(id);
        return 0;
    }

    public List<Script> getScriptByBancoDados(String bancoDados) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        try {
            BancoDados banco = BancoDados.valueOf(bancoDados.toUpperCase());
            return scriptRepository.findByUsuarioId(usuarioLogado.getId())
                    .stream()
                    .filter(s -> s.getBancoDados() == banco)
                    .toList();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Script> getScriptByCategoria(String categoria) {
        Usuario usuarioLogado = authService.getUsuarioLogado();

        try {
            Categoria catg = Categoria.valueOf(categoria.toUpperCase());
            return scriptRepository.findByUsuarioId(usuarioLogado.getId())
                    .stream()
                    .filter(s -> s.getCategoria() == catg)
                    .toList();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}