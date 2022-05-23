package com.example.K8s.web.auth.dto;

import lombok.Data;

@Data
public class UserLoginReqDto {
    private String email;
    private String password;
}
