package br.edu.unichristus.biblioteca_virtual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AutorRequestDTO {

    @NotBlank(message = "O nome do autor é obrigatório.")
    private String nome;

    @NotBlank(message = "A biografia é obrigatória.")
    private String biografia;

    @NotBlank(message = "A nacionalidade é obrigatória.")
    private String nacionalidade;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @PastOrPresent(message = "A data de nascimento não pode estar no futuro.")
    private LocalDate dataNascimento;
}