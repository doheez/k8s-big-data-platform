package com.example.K8s.web.cluster.dto;

import lombok.Data;

@Data
public class ClusterReqDto {
    private String type;
    private String name;
    private int amount;
}
