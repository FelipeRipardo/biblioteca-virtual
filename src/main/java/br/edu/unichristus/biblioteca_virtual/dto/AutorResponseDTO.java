package br.edu.unichristus.biblioteca_virtual.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AutorResponseDTO {

    private Long id;
    private String nome;
    private String biografia;
    private String nacionalidade;
    private LocalDate dataNascimento;

}