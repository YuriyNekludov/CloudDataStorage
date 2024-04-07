package edu.spring.clouddatastorage.exception;

public class UserAlreadyCreatedException extends RuntimeException {

    public UserAlreadyCreatedException(String message) {
        super(message);
    }
}
