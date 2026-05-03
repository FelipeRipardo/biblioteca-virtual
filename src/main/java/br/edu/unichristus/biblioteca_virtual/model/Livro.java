package br.edu.unichristus.biblioteca_virtual.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "ano_publicacao")
    private int anoPublicacao;

    @Column(name = "serie_recomendada", length = 200)
    private String serieRecomendada;

    @Column(name = "total_paginas", nullable = false)
    private int totalPaginas;

    @Column(length = 20)
    private String isbn;

    //Relacionamento N:1 - Vários livros podem pertencer a apenas um único Autor.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    //Relacionamento N:1 - Vários livros podem pertencer a uma única Categoria.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}
