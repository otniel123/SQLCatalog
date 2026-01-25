package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.model.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    public List<Script> listarScript(){
        listScript.sort(Comparator.comparing(Script::getDataAtualizacao).reversed());
        return this.listScript;
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
}

