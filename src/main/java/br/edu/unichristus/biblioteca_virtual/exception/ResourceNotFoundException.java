package br.edu.unichristus.biblioteca_virtual.exception;

//Erro será lançado sempre que não for possível encontrar um ID no banco
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}