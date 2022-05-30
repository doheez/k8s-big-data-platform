package com.example.K8s.web.cluster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PodDetailReqDto {
    private String clusterName;
    private String podName;
}
