package br.edu.unichristus.biblioteca_virtual.dto;

import lombok.Data;

@Data
public class LivroResponseDTO {

    private Long id;
    private String titulo;
    private String resumo;
    private Integer anoPublicacao;
    private Integer totalPaginas;
    private String isbn;
    private String serieRecomendada;

    // Devolvemos os objetos limpos, sem expor a estrutura interna do banco
    private AutorResponseDTO autor;
    private CategoriaResponseDTO categoria;

}