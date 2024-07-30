package com.security.login.service;

import com.security.login.config.SecurityConfig;
import com.security.login.dto.LoginResponseDTO;
import com.security.login.dto.UserDTO;
import com.security.login.dto.UserRequestDTO;
import com.security.login.entity.User;
import com.security.login.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Builder
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User signup(UserRequestDTO input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        setRoles(input, user);

        return userRepository.save(user);
    }

    public LoginResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String token = jwtService.generateToken(user);
        String message = "Hello " + (user.isAdmin() ? "ADMIN" : "USER");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setMessage(message);
        response.setUser(userDTO);

        return response;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User updateUser(UserRequestDTO requestDTO) {
        User existingUser = userRepository.findByUsername(requestDTO.getUsername());
        if (Objects.isNull(existingUser)) {
            throw new UsernameNotFoundException(requestDTO.getUsername());
        }
        existingUser.setUsername(requestDTO.getUsername());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setAdmin(requestDTO.getIsAdmin());
        setRoles(requestDTO, existingUser);

        userRepository.saveAndFlush(existingUser);
        return existingUser;
    }

    public void setRoles(UserRequestDTO requestDTO, User user) {
        if (Objects.nonNull(requestDTO.getIsAdmin()) && Objects.equals(true, requestDTO.getIsAdmin())) {
            user.setAdmin(requestDTO.getIsAdmin());

            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            roles.add("EMPLOYEE");

            user.setRoles(roles);
        } else {
            user.setAdmin(false);

            List<String> roles = new ArrayList<>();
            roles.add("EMPLOYEE");

            user.setRoles(roles);
        }
    }
}
