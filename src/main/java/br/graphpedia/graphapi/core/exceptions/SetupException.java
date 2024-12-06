package br.graphpedia.graphapi.core.exceptions;

public class SetupException extends RuntimeException {

    public SetupException(String message){
        super(message);
    }

    public SetupException(Exception exception){
        super(exception);
    }
}
