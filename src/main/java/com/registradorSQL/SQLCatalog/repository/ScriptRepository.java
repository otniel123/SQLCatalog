package com.registradorSQL.SQLCatalog.repository;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import com.registradorSQL.SQLCatalog.model.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script, Long> {

    @Query(value = "SELECT * FROM SCRIPT WHERE BANCO_DADOS = :banco_dados", nativeQuery = true)
    List<Script> listScriptByBancoDados(@Param("banco_dados")BancoDados bancoDados);

    @Query(value = "SELECT * FROM SCRIPT WHERE CATEGORIA = :categoria", nativeQuery = true)
    List<Script> listScriptByCategoria(@Param("categoria")Categoria categoria);

    @Query(value = "SELECT * FROM SCRIPT WHERE BANCO_DADOS = :banco_dados AND CATEGORIA = " +
            ":categoria", nativeQuery = true)
    List<Script> listScriptByBancoAndCategoria(@Param("banco_dados") BancoDados bancoDados,
                                               @Param("categoria") Categoria categoria);

    Page<Script> findAll(Pageable pageable);
}
