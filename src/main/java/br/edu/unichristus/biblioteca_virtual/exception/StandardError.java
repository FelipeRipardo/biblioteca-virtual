package br.edu.unichristus.biblioteca_virtual.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Classe para moldar o JSON de Exception(Erros). Independente do erro, retornará uma estrutura padrão.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}