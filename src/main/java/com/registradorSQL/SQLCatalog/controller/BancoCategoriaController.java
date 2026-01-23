package com.registradorSQL.SQLCatalog.controller;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import com.registradorSQL.SQLCatalog.service.BancoCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dominios")
public class BancoCategoriaController {

    @Autowired
    private BancoCategoriaService bancoCategoriaService;

    @RequestMapping("/bancos")
    public List<BancoDados> listaBancoDisponiveis(){
        return this.bancoCategoriaService.bancoDados();
    }

    @RequestMapping("/categorias")
    public List<Categoria> listaCategoriaDisponiveis(){
        return this.bancoCategoriaService.categoria();
    }
}
