package edu.spring.clouddatastorage.exception;

public class EmptySearchQueryException extends RuntimeException {

    public EmptySearchQueryException(String message) {
        super(message);
    }
}
