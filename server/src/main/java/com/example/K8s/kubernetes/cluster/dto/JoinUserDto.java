package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinUserDto {
    private Long userId;
    private String clusterName;
}
