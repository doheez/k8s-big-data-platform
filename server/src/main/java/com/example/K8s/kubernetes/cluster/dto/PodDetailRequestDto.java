package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PodDetailRequestDto {
    private String clusterName;
    private String podName;
}