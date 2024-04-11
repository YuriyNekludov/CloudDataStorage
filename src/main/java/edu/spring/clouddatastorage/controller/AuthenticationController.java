package edu.spring.clouddatastorage.controller;

import edu.spring.clouddatastorage.dto.UserRegistrationDto;
import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.service.FolderService;
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
    private final FolderService folderService;

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserRegistrationDto user) {
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("user") @Valid UserRegistrationDto user,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "authentication/registration";
        if (!user.password().equals(user.repeatPassword()))
            throw new PasswordNotMatchingException("Введенные пароли должны совпадать.");
        var userDtoResponse = userService.create(user);
        folderService.createUserRootFolder(userDtoResponse);
        redirectAttributes.addAttribute("successMessage",
                "Регистрация прошла успешно. Теперь вы можете авторизоваться.");
        return "redirect:/login";
    }
}
