package edu.spring.clouddatastorage.service;

import edu.spring.clouddatastorage.mapper.UserMapper;
import edu.spring.clouddatastorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::dtoFromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }
}
