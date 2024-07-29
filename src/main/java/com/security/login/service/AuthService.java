package com.security.login.service;

import com.security.login.entity.User;
import com.security.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service
@Builder
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);

    }
}
