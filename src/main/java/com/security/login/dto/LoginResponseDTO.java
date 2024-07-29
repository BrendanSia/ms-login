package com.security.login.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private String token;
    private String message;
    private UserDTO user;
    private String errorMessage;
}
