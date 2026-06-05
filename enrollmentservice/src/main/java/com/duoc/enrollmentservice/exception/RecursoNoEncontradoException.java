package com.duoc.enrollmentservice.exception;

//  Excepción personalizada para indicar que un recurso no fue encontrado
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}