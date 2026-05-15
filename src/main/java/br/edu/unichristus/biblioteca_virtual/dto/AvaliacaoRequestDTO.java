package br.edu.unichristus.biblioteca_virtual.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AvaliacaoRequestDTO {

    @NotBlank(message = "O apelido do leitor é obrigatório.")
    private String apelidoLeitor;

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima permitida é 1.")
    @Max(value = 5, message = "A nota máxima permitida é 5.")
    private Integer nota;

    @NotBlank(message = "O comentário é obrigatório.")
    private String comentario;

    @NotNull(message = "O ID do livro é obrigatório.")
    private Long livroId;
}