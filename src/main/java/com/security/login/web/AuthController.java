package com.security.login.web;

import com.security.login.config.PasswordEncoderConfig;
import com.security.login.dto.BaseResponseDTO;
import com.security.login.dto.LoginRequestDTO;
import com.security.login.dto.LoginResponseDTO;
import com.security.login.entity.User;
import com.security.login.repository.UserRepository;
import com.security.login.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    @Value("${jwt.secret}")
    private String secretKey;
    private final AuthService authService;
    private final PasswordEncoderConfig encoderConfig;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        // Simple validation for development purposes.
        // Will implement more complex methods further.
        User user = authService.findByUsername(loginRequest.getUsername());
        if (user == null || !encoderConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword()))
        {
            LoginResponseDTO response = new LoginResponseDTO();
            response.setErrorMessage("Invalid Credentials");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = generateToken(user);
        String message = "Hello " + (user.isAdmin() ? "ADMIN" : "USER");

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(token);
        response.setMessage(message);

        return ResponseEntity.ok(response);
    }

    private String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.isAdmin() ? "ADMIN" : "USER");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}