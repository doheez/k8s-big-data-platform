package com.example.K8s.web.cluster.dto;

import lombok.Data;

@Data
public class PodDetailResDto {
    private String name;
    private String namespace;
    private String nodeName;
    private String hostIP;
    private String startTime;
    private String status;
    private String podIP;
}
