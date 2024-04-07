package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.exception.PasswordNotMatchingException;
import edu.spring.clouddatastorage.dto.UserCreateDto;
import edu.spring.clouddatastorage.mapper.UserMapper;
import edu.spring.clouddatastorage.model.Role;
import edu.spring.clouddatastorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::dtoFromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public void create(UserCreateDto userDto) {
        if (!userDto.password().equals(userDto.repeatPassword()))
            throw new PasswordNotMatchingException("Введенные пароли должны совпадать.");
        var user = userMapper.entityFromDto(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
