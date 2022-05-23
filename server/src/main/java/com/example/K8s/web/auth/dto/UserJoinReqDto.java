package com.example.K8s.web.auth.dto;

import lombok.Data;

@Data
public class UserJoinReqDto {
    private String email;
    private String name;
    private String password;
}
