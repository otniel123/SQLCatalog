package com.registradorSQL.SQLCatalog.service;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BancoCategoriaService {

    private BancoDados bancoDados;

    private Categoria categoria;

    public List<BancoDados> bancoDados(){
        return Arrays.asList(BancoDados.values());
    }

    public List<Categoria> categoria(){
        return Arrays.asList(Categoria.values());
    }
}
