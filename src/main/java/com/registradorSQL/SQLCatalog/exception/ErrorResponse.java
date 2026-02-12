package com.registradorSQL.SQLCatalog.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timeStamp;
    private int status;
    private String erro;
    private String path;

    public ErrorResponse(){}

    public ErrorResponse(LocalDateTime timeStamp, int status, String erro, String path) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.erro = erro;
        this.path = path;
    }

    public ErrorResponse( int status, String erro, String path) {
        this.status = status;
        this.erro = erro;
        this.path = path;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
