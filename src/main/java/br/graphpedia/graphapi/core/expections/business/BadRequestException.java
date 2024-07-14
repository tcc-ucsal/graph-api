package br.graphpedia.graphapi.core.expections.business;

public class BadRequestException  extends RuntimeException {

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message) {
        super(message);
    }
}
