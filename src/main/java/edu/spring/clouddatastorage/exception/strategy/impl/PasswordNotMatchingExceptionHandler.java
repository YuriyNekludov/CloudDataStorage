package edu.spring.clouddatastorage.exception.strategy.impl;

import edu.spring.clouddatastorage.exception.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class PasswordNotMatchingExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public String handeException(HttpServletResponse resp, RedirectAttributes redirectAttributes, Exception e) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        redirectAttributes.addAttribute("message", e.getMessage());
        return "redirect:/registration";
    }
}
