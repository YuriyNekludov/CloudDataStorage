package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.UserCreateDto;
import edu.spring.clouddatastorage.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping()
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserCreateDto user) {
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("user") @Valid UserCreateDto user,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "authentication/registration";
        userService.create(user);
        redirectAttributes.addAttribute("successMessage",
                "Регистрация прошла успешно. Теперь вы можете авторизоваться.");
        return "redirect:/login";
    }
}
