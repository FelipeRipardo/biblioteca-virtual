package br.edu.unichristus.biblioteca_virtual.dto;

import lombok.Data;

@Data
public class AvaliacaoResponseDTO {

    private Long id;
    private String apelidoLeitor;
    private Integer nota;
    private String comentario;

    //Devolve o livro formatado e limpo.
    private LivroResponseDTO livro;
}