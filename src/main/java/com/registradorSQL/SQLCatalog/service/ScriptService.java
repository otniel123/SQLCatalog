package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import com.registradorSQL.SQLCatalog.model.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ScriptService {


    List<Script> listScript = new ArrayList<>();

    private Long proximoId = 1L;

    public Script criaScript(Script script){
        script.setId(proximoId++);
        script.setDataCriacao(LocalDateTime.now());
        script.setDataAtualizacao(LocalDateTime.now());
        listScript.add(script);
        return script;
    }

    public List<Script> listarScript(String banco, String categoria, String texto, String tag){
        Stream<Script> stream = listScript.stream();
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
        stream = stream.sorted(Comparator.comparing(Script::getDataAtualizacao).reversed());
        return stream.toList();
    }

    public Script listarScriptById(Long id){
        for (Script s : listScript){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }

    public Script atualizarScript(Script script, Long id){
            for (int i = 0; i<= listScript.size(); i++){
                if (listScript.get(i).getId().equals(id)){
                    script.setId(listScript.get(i).getId());
                    script.setDataAtualizacao(LocalDateTime.now());
                    script.setDataCriacao(listScript.get(i).getDataCriacao());
                    listScript.set(i, script);
                    return script;
                }
        }
        return null;
    }

    public Integer deleteScript(Long id){
        for (Script s : listScript){
            if (s.getId() == id){
                listScript.remove(s);
                return 0;
            }
        }
        return 1;
    }

    public List<Script> getScriptByBancoDados(String bancoDados){
        try {
            BancoDados banco = BancoDados.valueOf(bancoDados.toUpperCase());
            return listScript.stream().filter(s -> s.getBancoDados() == banco).toList();
        }catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Script> getScriptByCategoria(String categoria){
        try{
            Categoria catg = Categoria.valueOf(categoria.toUpperCase());
            return listScript.stream().filter(s -> s.getCategoria() == catg).toList();
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}

