package com.wilker.sistemavendas.exception.errors;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }

    public UnauthorizedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
