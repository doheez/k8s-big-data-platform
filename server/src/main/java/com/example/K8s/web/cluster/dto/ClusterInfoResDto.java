package com.example.K8s.web.cluster.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClusterInfoResDto {
    private int type;
    private String clusterName;
    private List<PodInfo> pods = new ArrayList<>();
}
