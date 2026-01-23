package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.service.ApiHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiHealthController {

    @Autowired
    private ApiHealthService apiHealthService;

    @GetMapping
    @RequestMapping("/health")
    public String apiHealth(){
        return this.apiHealthService.healthApi();
    }
}
