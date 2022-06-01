package com.example.K8s.web.cluster.dto;

import lombok.Data;

@Data
public class ClusterUserInfoDto {
    private String name;
    private String email;

    public ClusterUserInfoDto(String name, String email){
        this.name = name;
        this.email = email;
    }
}
