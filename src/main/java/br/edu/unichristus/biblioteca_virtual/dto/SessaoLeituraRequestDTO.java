package br.edu.unichristus.biblioteca_virtual.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessaoLeituraRequestDTO {

    @NotNull(message = "O ID do livro é obrigatório para iniciar a leitura.")
    private Long livroId;

}