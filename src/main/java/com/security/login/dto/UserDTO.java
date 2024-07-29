package com.security.login.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String email;
    private List<String> roles;
}
