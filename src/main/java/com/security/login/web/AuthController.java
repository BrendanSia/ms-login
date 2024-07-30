package com.security.login.web;

import com.security.login.dto.BaseResponseDTO;
import com.security.login.dto.LoginRequestDTO;
import com.security.login.dto.LoginResponseDTO;
import com.security.login.dto.UserRequestDTO;
import com.security.login.entity.User;
import com.security.login.exceptions.InsufficientPermissionExcpetion;
import com.security.login.exceptions.InvalidTokenException;
import com.security.login.service.AuthService;
import com.security.login.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(
            @RequestBody UserRequestDTO userRequestDTO) {
        User registeredUser = authService.signup(userRequestDTO);

        return ResponseEntity.ok(registeredUser);
    }


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

    @GetMapping("/all")
    public ResponseEntity<BaseResponseDTO> allUsers(
            @RequestHeader(name = "jwt-token") String token
    ) {
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        // Validate token
        try {

            validateAdminToken(token);

            List <User> users = authService.allUsers();
            responseDTO.setStatus("SUCCESS");
            responseDTO.setData(users);
            return ResponseEntity.ok(responseDTO);

        } catch (InvalidTokenException e) {
            responseDTO.setStatus("FAIL");
            responseDTO.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        } catch (InsufficientPermissionExcpetion e) {
            responseDTO.setStatus("FAIL");
            responseDTO.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }

    @PostMapping(value="/update")
    public ResponseEntity<BaseResponseDTO> updateUser(
            @RequestHeader(name = "jwt-token") String token,
            @Valid @RequestBody UserRequestDTO requestDTO
    ){
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        try {
            validateAdminToken(token);
            User updateUser = authService.updateUser(requestDTO);

            responseDTO.setStatus("SUCCESS");
            responseDTO.setData(updateUser);

            return ResponseEntity.ok(responseDTO);
        } catch (InvalidTokenException e) {
            responseDTO.setStatus("FAIL");
            responseDTO.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        } catch (InsufficientPermissionExcpetion e) {
            responseDTO.setStatus("FAIL");
            responseDTO.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }

    public void validateAdminToken(String token) throws InvalidTokenException, InsufficientPermissionExcpetion {
        Claims claims = jwtService.extractAllClaims(token);
        if (!jwtService.controllerCheckToken(token, claims.getSubject())) {
            throw new InvalidTokenException();
        }
        if (!jwtService.extractRoles(token).contains("ROLE_ADMIN")) {
            throw new InsufficientPermissionExcpetion();
        }
    }
}