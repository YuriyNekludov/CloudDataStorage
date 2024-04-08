package edu.spring.clouddatastorage.interceptor;

import edu.spring.clouddatastorage.security.UserDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInfoInterceptor implements HandlerInterceptor {

    @SneakyThrows
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        if (modelAndView != null) {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                var userId = ((UserDetailsDto) authentication.getPrincipal()).getId();
                modelAndView.addObject("username", authentication.getName());
                modelAndView.addObject("userId", userId);
            }
        }
    }
}
