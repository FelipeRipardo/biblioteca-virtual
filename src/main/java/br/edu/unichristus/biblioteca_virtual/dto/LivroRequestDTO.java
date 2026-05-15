package br.edu.unichristus.biblioteca_virtual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LivroRequestDTO {

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "O resumo é obrigatório.")
    private String resumo;

    @NotNull(message = "O ano de publicação é obrigatório.")
    private Integer anoPublicacao;

    @NotNull(message = "O total de páginas é obrigatório.")
    @Positive(message = "O total de páginas deve ser maior que zero.")
    private Integer totalPaginas;

    @NotBlank(message = "O ISBN é obrigatório.")
    private String isbn;

    private String serieRecomendada;

    @NotNull(message = "O ID do autor é obrigatório.")
    private Long autorId;

    @NotNull(message = "O ID da categoria é obrigatório.")
    private Long categoriaId;
}