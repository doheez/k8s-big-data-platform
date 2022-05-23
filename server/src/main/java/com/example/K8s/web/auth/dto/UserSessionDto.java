package com.example.K8s.web.auth.dto;

import com.example.K8s.web.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String name;
    private String email;
    private String image;

    @Builder
    public UserSessionDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.image = user.getImage();
    }
}
