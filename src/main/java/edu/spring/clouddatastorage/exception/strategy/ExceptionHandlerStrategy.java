package edu.spring.clouddatastorage.exception.strategy;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ExceptionHandlerStrategy {

    String handeException(HttpServletResponse resp,
                          RedirectAttributes redirectAttributes,
                          Exception e);
}
