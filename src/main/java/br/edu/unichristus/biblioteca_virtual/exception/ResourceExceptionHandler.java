package br.edu.unichristus.biblioteca_virtual.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

//A função dessa classe é "ouvir" toda a aplicação.
//Se for lançado a ResourceNotFoundException, ele pega, monta o StandardError e devolve um 404 (Not Found) padronizado.
@ControllerAdvice
public class ResourceExceptionHandler {

    //1 - Trata o erro personalizado de ID não encontrado (Erro 404).
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //2 - Trata erros do banco de dados, como tentar salvar um livro com Autor que não existe (Erro 409 ou 400)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Violação de integridade de dados",
                "Você tentou realizar uma operação que fere as regras do banco de dados (Ex: ID de relacionamento inexistente ou dados duplicados).",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    //3 - Trata qualquer outro erro inesperado (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> globalException(Exception e, HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Contate o administrador do sistema.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}