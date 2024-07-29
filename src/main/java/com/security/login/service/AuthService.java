package com.security.login.service;

import com.security.login.config.SecurityConfig;
import com.security.login.dto.LoginResponseDTO;
import com.security.login.dto.UserDTO;
import com.security.login.entity.User;
import com.security.login.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Builder
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private SecurityConfig securityConfig;

    public LoginResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String token = generateToken(user);
        String message = "Hello " + (user.isAdmin() ? "ADMIN" : "USER");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setMessage(message);
        response.setUser(userDTO);

        return response;
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.isAdmin() ? "ADMIN" : "USER");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, securityConfig.secretKey().getBytes())
                .compact();
    }
}
