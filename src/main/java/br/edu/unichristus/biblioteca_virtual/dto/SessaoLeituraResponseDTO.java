package br.edu.unichristus.biblioteca_virtual.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoLeituraResponseDTO {

    private String tokenDispositivo;
    private Integer ultimaPaginaLida;
    private LocalDateTime dataInicio;
    private LocalDateTime ultimoAcesso;

    //Devolve os dados do livro que está sendo lido
    private LivroResponseDTO livro;

}