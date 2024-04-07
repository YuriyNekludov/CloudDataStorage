package edu.spring.clouddatastorage;

import edu.spring.clouddatastorage.dto.UserCreateDto;
import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.exception.UserAlreadyCreatedException;
import edu.spring.clouddatastorage.repository.UserRepository;
import edu.spring.clouddatastorage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest extends CloudDataStorageApplicationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void registerNewUserShouldBeSuccessful() {
        var userDto = UserCreateDto.builder()
                .username("ivanov")
                .password("123")
                .repeatPassword("123")
                .build();
        userService.create(userDto);
        var user = userRepository.findByUsername(userDto.username());
        assertThat(user).isNotNull();
    }

    @Test
    public void loginUserShouldBeSuccessful() {
        var userDto = UserCreateDto.builder()
                .username("ivanov")
                .build();
        assertDoesNotThrow(() -> userService.loadUserByUsername(userDto.username()));
    }

    @Test
    public void registerUserWithMismatchesPasswordShouldBeThrowException() {
        var userDto = UserCreateDto.builder()
                .username("juravlev")
                .password("123")
                .repeatPassword("321")
                .build();
        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(PasswordNotMatchingException.class);
    }

    @Test
    public void registerUserWithNotUniqueUsernameShouldBeThrowException() {
        var userDto = UserCreateDto.builder()
                .username("ivanov")
                .password("123")
                .repeatPassword("123")
                .build();
        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(UserAlreadyCreatedException.class);
    }
}
