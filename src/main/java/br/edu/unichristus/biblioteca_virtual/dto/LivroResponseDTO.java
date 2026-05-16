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

    private String nomeAutor;

    private String nomeCategoria;
    private String areaConhecimentoCategoria;
    private String departamentoResponsavelCategoria;

}