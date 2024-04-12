package edu.spring.clouddatastorage;

import edu.spring.clouddatastorage.dto.UserRegistrationDto;
import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.exception.UserAlreadyCreatedException;
import edu.spring.clouddatastorage.repository.UserRepository;
import edu.spring.clouddatastorage.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest extends CloudDataStorageApplicationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Test
    public void registerNewUserShouldBeSuccessful() {
        var userDto = UserRegistrationDto.builder()
                .username("ivanov")
                .password("123")
                .repeatPassword("123")
                .build();
        userService.create(userDto);
        assertDoesNotThrow(() -> userService.loadUserByUsername(userDto.username()));
    }

    @Test
    @Disabled
    public void registerUserWithMismatchesPasswordShouldBeThrowException() {
        var userDto = UserRegistrationDto.builder()
                .username("juravlev")
                .password("123")
                .repeatPassword("321")
                .build();
        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    public void registerUserWithNotUniqueUsernameShouldBeThrowException() {
        var userDto = UserRegistrationDto.builder()
                .username("ivanov")
                .password("123")
                .repeatPassword("123")
                .build();
        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(UserAlreadyCreatedException.class);
    }
}
