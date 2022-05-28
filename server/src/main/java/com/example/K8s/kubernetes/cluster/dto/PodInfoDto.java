package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PodInfoDto {
    private String name;
    private String PodIP;
    private String STATUS;

    public PodInfoDto(String name, String PodIP, String STATUS){
        this.name = name;
        this.PodIP = PodIP;
        this.STATUS = STATUS;
    }
}
