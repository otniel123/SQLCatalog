package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.model.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScriptService {

    @Autowired
    private Script script;

    private Long proximoId = 1L;

    public Script criaScript(Script script){
        this.script.setId(proximoId++);
        this.script.setTitulo(script.getTitulo());
        this.script.setDescricao(script.getDescricao());
        this.script.setConteudo(script.getConteudo());
        this.script.setBancoDados(script.getBancoDados());
        this.script.setCategoria(script.getCategoria());
        this.script.setTags(script.getTags());
        this.script.setDataCriacao(LocalDateTime.now());
        this.script.setDataAtualizacao(LocalDateTime.now());
        return this.script;
    }
}

