package br.graphpedia.graphapi.core.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(Exception e){
        super(e);
    }
}
