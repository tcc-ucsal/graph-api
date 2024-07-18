package br.graphpedia.graphapi.core.expections;

public class PersistenceException extends RuntimeException {


    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
