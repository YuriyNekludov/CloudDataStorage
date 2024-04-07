package edu.spring.clouddatastorage.exception.strategy;

import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.exception.strategy.impl.AnotherExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.PasswordNotMatchingExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerStrategyFactory {

    public ExceptionHandlerStrategy getExceptionHandlerStrategy(Exception e) {
        if (e instanceof PasswordNotMatchingException)
            return new PasswordNotMatchingExceptionHandler();
        else
            return new AnotherExceptionHandler();
    }
}
