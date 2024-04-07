package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.exception.strategy.ExceptionHandlerStrategyFactory;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@RequiredArgsConstructor
public class ApplicationControllerAdvice {

    private final ExceptionHandlerStrategyFactory strategyFactory;

    @ExceptionHandler()
    public String handleException(Exception e,
                                  HttpServletResponse resp,
                                  RedirectAttributes redirectAttributes) {
        var exceptionStrategy = strategyFactory.getExceptionHandlerStrategy(e);
        return exceptionStrategy.handeException(resp, redirectAttributes, e);
    }
}
