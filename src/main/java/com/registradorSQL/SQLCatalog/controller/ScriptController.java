package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.service.ScriptService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @PostMapping
    public ResponseEntity<Script> criarScript(@RequestBody Script script){
        return ResponseEntity.status(201).body(scriptService.criaScript(script));
    }

    @GetMapping
    public ResponseEntity<List<Script>> getScript(@RequestParam(required = false) String banco,
                                                  @RequestParam(required = false) String categoria,
                                                  @RequestParam(required = false) String texto,
                                                  @RequestParam(required = false) String tag){
        if (scriptService.listarScript(banco, categoria, texto,tag) == null){
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(scriptService.listarScript(banco, categoria, texto
                , tag));
    }



    @GetMapping("{id}")
    public ResponseEntity<Script> getScriptById(@PathVariable("id") Long id){
        if (scriptService.listarScriptById(id) != null){
            return ResponseEntity.status(200).body(scriptService.listarScriptById(id));
        }
        return ResponseEntity.status(404).body(null);
    }

    @PutMapping("{id}")
    public ResponseEntity<Script> atualizarScript(@RequestBody Script script,
                                                  @PathVariable("id") Long id){
        if (scriptService.atualizarScript(script, id) != null){
            return ResponseEntity.status(200).body(script);
        }
        return ResponseEntity.status(404).body(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Script> deletarScript(@PathVariable("id") Long id){
        if (scriptService.deleteScript(id) == 0){
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.status(404).body(null);
    }
}
