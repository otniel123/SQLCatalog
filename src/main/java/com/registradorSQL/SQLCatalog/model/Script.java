package com.registradorSQL.SQLCatalog.model;

import com.registradorSQL.SQLCatalog.enu.BancoDados;
import com.registradorSQL.SQLCatalog.enu.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Script")
public class Script implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @Size(max = 500, message = "Descrição só pode ter até 500 caracteres")
    private String descricao;

    @NotBlank(message = "Conteúdo é obrigatório")
    private String conteudo;

    @NotNull(message = "Banco de dados é obrigatório")
    @Enumerated(EnumType.STRING)
    private BancoDados bancoDados;

    @NotNull(message = "Categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<String> tags = new ArrayList<>();
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Script() {
    }

    public Script(Long id, String titulo, String descricao, String conteudo, BancoDados bancoDados, Categoria categoria, List<String> tags, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.conteudo = conteudo;
        this.bancoDados = bancoDados;
        this.categoria = categoria;
        this.tags = tags;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public BancoDados getBancoDados() {
        return bancoDados;
    }

    public void setBancoDados(BancoDados bancoDados) {
        this.bancoDados = bancoDados;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Script script)) return false;
        return Objects.equals(id, script.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Script{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", conteudo='" + conteudo + '\'' +
                ", bancoDados=" + bancoDados +
                ", categoria=" + categoria +
                ", tags=" + tags +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}

