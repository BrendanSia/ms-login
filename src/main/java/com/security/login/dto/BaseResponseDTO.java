package com.security.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseDTO {
    private LoginResponseDTO login;
    private String token;

    private String errorMessage;
}
