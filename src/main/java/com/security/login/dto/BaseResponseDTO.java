package com.security.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseDTO {
    private String status;
    private Object data;
}
