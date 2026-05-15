package br.edu.unichristus.biblioteca_virtual.dto;

import lombok.Data;

@Data
public class CategoriaResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String areaConhecimento;
    private String departamentoResponsavel;

}