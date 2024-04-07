package edu.spring.clouddatastorage.exception;

public class PasswordNotMatchingException extends RuntimeException {

    public PasswordNotMatchingException(String message) {
        super(message);
    }
}
