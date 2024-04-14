package edu.spring.clouddatastorage.exception.strategy;

import edu.spring.clouddatastorage.exception.DuplicateNameException;
import edu.spring.clouddatastorage.exception.EmptySearchQueryException;
import edu.spring.clouddatastorage.exception.MinioInteractionException;
import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.exception.UserAlreadyCreatedException;
import edu.spring.clouddatastorage.exception.strategy.impl.AnotherExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.DuplicateNameExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.EmptySearchQueryExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.MinioInteractionExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.PasswordNotMatchingExceptionHandler;
import edu.spring.clouddatastorage.exception.strategy.impl.UserAlreadyCreatedExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerStrategyFactory {

    public ExceptionHandlerStrategy getExceptionHandlerStrategy(Exception e) {
        if (e instanceof PasswordNotMatchingException)
            return new PasswordNotMatchingExceptionHandler();
        if (e instanceof UserAlreadyCreatedException)
            return new UserAlreadyCreatedExceptionHandler();
        if (e instanceof MinioInteractionException)
            return new MinioInteractionExceptionHandler();
        if (e instanceof DuplicateNameException)
            return new DuplicateNameExceptionHandler();
        if (e instanceof EmptySearchQueryException)
            return new EmptySearchQueryExceptionHandler();
        return new AnotherExceptionHandler();
    }
}
