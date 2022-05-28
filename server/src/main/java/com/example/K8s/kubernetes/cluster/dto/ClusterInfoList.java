package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClusterInfoList {
    private List<ClusterInfoListDto> clusters;

    public ClusterInfoList(List<ClusterInfoListDto> dto) {
        this.clusters = dto;
    }
}
