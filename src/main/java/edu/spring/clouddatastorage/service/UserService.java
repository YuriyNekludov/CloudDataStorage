package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.dto.UserCreateDto;
import edu.spring.clouddatastorage.exception.UserAlreadyCreatedException;
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
    private final FileManagerService fileManagerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::dtoFromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    public void create(UserCreateDto userDto) {
        var user = userMapper.entityFromDto(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(Role.USER);
        try {
            user = userRepository.save(user);
            var rootFolderName = "user-" + user.getId() + "-files";
            fileManagerService.createNewFolder(rootFolderName);
        } catch (Exception e) {
            throw new UserAlreadyCreatedException("Пользователь с username \""
                    + userDto.username() + "\" уже существует.");
        }
    }
}
