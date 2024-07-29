package com.security.login.web;

import com.security.login.config.ApplicationConfiguration;
import com.security.login.dto.LoginRequestDTO;
import com.security.login.dto.LoginResponseDTO;
import com.security.login.entity.User;
import com.security.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest
    ) {
        // Simple validation for development purposes.
        // Will implement more complex methods further.
        try {
            return ResponseEntity.ok(authService.findByUsername(loginRequest.getUsername()));
        } catch (UsernameNotFoundException ex) {
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setErrorMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getDetails();

        return ResponseEntity.ok(currentUser);
    }
}