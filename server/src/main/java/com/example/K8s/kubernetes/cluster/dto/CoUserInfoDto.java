package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoUserInfoDto {
    private String name;
    private String email;

    public CoUserInfoDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
