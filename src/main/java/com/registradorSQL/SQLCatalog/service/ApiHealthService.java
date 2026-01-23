package com.registradorSQL.SQLCatalog.service;

import org.springframework.stereotype.Service;

@Service
public class ApiHealthService {
    String messageApiHealth = "{status: online, aplicacao: SQL Catalog}";
    public String healthApi(){
        return this.messageApiHealth;
    }
}
