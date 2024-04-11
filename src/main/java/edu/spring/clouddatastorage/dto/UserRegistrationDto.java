package edu.spring.clouddatastorage.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Builder
public record UserRegistrationDto(

        @NotBlank(message = "Поле \"username\" не должно быть пустым.")
        @Size(min = 4, max = 25, message = "Поле \"username\" должно содержать от 4 до 25 символов.")
        String username,

        @NotBlank(message = "Поле \"password\" не должно быть пустым.")
        @Size(min = 3, max = 20, message = "Поле \"password\" должно содержать от 3-20 символов.")
        @Pattern(regexp = "[\\dA-Za-zА-Яа-я]+", message = "Поле \"password\" должно состоять только из цифр и букв.")
        String password,

        @NotBlank( message = "Поле \"repeat password\" не должно быть пустым.")
        @Size(min = 3, max = 20, message = "Поле \"repeat password\" должно содержать от 3-20 символов.")
        String repeatPassword) {
}
