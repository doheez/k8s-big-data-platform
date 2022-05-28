package com.example.K8s.web.cluster.dto;

import lombok.Data;

@Data
public class PodDetailReqDto {
    private String clusterName;
    private String podName;
}
