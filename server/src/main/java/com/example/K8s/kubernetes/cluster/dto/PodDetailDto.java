package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PodDetailDto {
    private String name;
    private String namespace;
    private String nodeName;
    private String nodeIP;
    private String startTime;
    private String status;
    private String IP;
}