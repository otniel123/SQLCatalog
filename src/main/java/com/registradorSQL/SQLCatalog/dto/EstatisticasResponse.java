package com.registradorSQL.SQLCatalog.dto;

import java.util.Map;

public class EstatisticasResponse {

    private long totalUsuarios;
    private long usuariosAtivos;
    private long totalScripts;
    private Map<String, Long> scriptsPorBanco;
    private Map<String, Long> scriptsPorCategoria;

    public EstatisticasResponse() {
    }

    public EstatisticasResponse(long totalUsuarios, long usuariosAtivos, long totalScripts,
                                Map<String, Long> scriptsPorBanco, Map<String, Long> scriptsPorCategoria) {
        this.totalUsuarios = totalUsuarios;
        this.usuariosAtivos = usuariosAtivos;
        this.totalScripts = totalScripts;
        this.scriptsPorBanco = scriptsPorBanco;
        this.scriptsPorCategoria = scriptsPorCategoria;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public long getUsuariosAtivos() {
        return usuariosAtivos;
    }

    public void setUsuariosAtivos(long usuariosAtivos) {
        this.usuariosAtivos = usuariosAtivos;
    }

    public long getTotalScripts() {
        return totalScripts;
    }

    public void setTotalScripts(long totalScripts) {
        this.totalScripts = totalScripts;
    }

    public Map<String, Long> getScriptsPorBanco() {
        return scriptsPorBanco;
    }

    public void setScriptsPorBanco(Map<String, Long> scriptsPorBanco) {
        this.scriptsPorBanco = scriptsPorBanco;
    }

    public Map<String, Long> getScriptsPorCategoria() {
        return scriptsPorCategoria;
    }

    public void setScriptsPorCategoria(Map<String, Long> scriptsPorCategoria) {
        this.scriptsPorCategoria = scriptsPorCategoria;
    }
}