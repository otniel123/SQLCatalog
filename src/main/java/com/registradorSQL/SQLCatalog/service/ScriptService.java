package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.repository.ScriptRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ScriptService {


    List<Script> listScript = new ArrayList<>();

    @Autowired
    ScriptRepository scriptRepository;

    private Long proximoId = 1L;

    @Transactional
    public Script criaScript(Script script){
        script.setDataCriacao(LocalDateTime.now());
        script.setDataAtualizacao(LocalDateTime.now());
        return scriptRepository.save(script);
    }

    public List<Script> listarScript(String banco, String categoria, String texto, String tag,
                                     Integer page, Integer size){
        Stream<Script> stream = scriptRepository.findAll(PageRequest.of(page, size)).stream();
        if (banco != null && !banco.isEmpty()){
            try {
                BancoDados bancoDados = BancoDados.valueOf(banco.toUpperCase());
                stream = stream.filter(s -> s.getBancoDados() == bancoDados);
            }catch (IllegalArgumentException e) {
                Collections.emptyList();
            }
        }
        if (categoria != null && !categoria.isEmpty()){
            try{
                Categoria catg = Categoria.valueOf(categoria.toUpperCase());
                stream = stream.filter(s -> s.getCategoria() == catg);
            }catch (IllegalArgumentException e){
                Collections.emptyList();
            }
        }
        if (texto != null && !texto.isEmpty()){
            try {
                stream =
                        stream.filter(script -> script.getDescricao() != null && script.getDescricao().toUpperCase().contains(texto.toUpperCase()) ||
                                      script.getConteudo() != null && script.getConteudo().toUpperCase().contains(texto.toUpperCase()) ||
                                      script.getTitulo() != null && script.getTitulo().toUpperCase().contains(texto.toUpperCase()));
            }catch (IllegalArgumentException e){
                Collections.emptyList();
            }
        }
        if (tag != null && !tag.isEmpty()){
            try {
                stream =
                        stream.filter(script -> script.getTags().stream().anyMatch(s -> s.equalsIgnoreCase(tag)));
            }catch (IllegalArgumentException e){
                return Collections.emptyList();
            }
        }
        stream = stream.sorted(Comparator.comparing(Script::getDataAtualizacao,
                Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
        return stream.toList();
    }

    @Transactional
    public Script listarScriptById(Long id){
        for (Script s : scriptRepository.findAll()){
            if (s.getId() == id){
                return scriptRepository.getById(id);
            }
        }
        return null;
    }

    public Script atualizarScript(Script script, Long id){
        Optional<Script> optionalScript = scriptRepository.findById(id);

        if (optionalScript.isEmpty()) {
            return null;
        }

        Script scriptOriginal = optionalScript.get();

        script.setId(id);
        script.setDataCriacao(scriptOriginal.getDataCriacao());
        script.setDataAtualizacao(LocalDateTime.now());

        return scriptRepository.save(script);
    }

    public Integer deleteScript(Long id){
            if (scriptRepository.existsById(id)){
                scriptRepository.deleteById(id);
                return 0;
            }
        return 1;
    }

    public List<Script> getScriptByBancoDados(String bancoDados){
        try {
            BancoDados banco = BancoDados.valueOf(bancoDados.toUpperCase());
            return scriptRepository.listScriptByBancoDados(banco);
        }catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Script> getScriptByCategoria(String categoria){
        try{
            Categoria catg = Categoria.valueOf(categoria.toUpperCase());
            return scriptRepository.listScriptByCategoria(catg);
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}

