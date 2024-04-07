package edu.spring.clouddatastorage.exception.strategy.impl;

import edu.spring.clouddatastorage.exception.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
public class AnotherExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public String handeException(HttpServletResponse resp, RedirectAttributes redirectAttributes, Exception e) {
        log.warn("Something went wrong while handling an exception: {}", e.getStackTrace());
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        redirectAttributes.addAttribute("message", "Что-то пошло не так :(");
        return "redirect:/error";
    }
}
