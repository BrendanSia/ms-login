package com.security.login.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private Boolean isAdmin;
}
