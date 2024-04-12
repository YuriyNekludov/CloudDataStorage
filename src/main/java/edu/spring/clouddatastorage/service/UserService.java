package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDtoResponse create(UserRegistrationDto userDto);
}
