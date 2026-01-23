package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.model.Script;
import com.registradorSQL.SQLCatalog.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @PostMapping
    public ResponseEntity<Script> criarScript(@RequestBody Script script){
        return ResponseEntity.status(201).body(scriptService.criaScript(script));
    }
}
