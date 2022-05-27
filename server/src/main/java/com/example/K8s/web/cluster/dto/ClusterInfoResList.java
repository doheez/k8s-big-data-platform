package com.example.K8s.web.cluster.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClusterInfoResList {
    private List<ClusterInfoResDto> clusters;

    public ClusterInfoResList(){
        clusters = new ArrayList<>();
    }
}
