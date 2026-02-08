package com.registradorSQL.SQLCatalog.repository;

import com.registradorSQL.SQLCatalog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
