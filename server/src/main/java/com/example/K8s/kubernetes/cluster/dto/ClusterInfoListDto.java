package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ClusterInfoListDto {
    private int type;
    private String clusterName;
    private List<PodInfoDto> clueterInfo = new ArrayList<>();
    public ClusterInfoListDto(int type, String clusterName, List<PodInfoDto> clueterInfo){
        this.type = type;
        this.clusterName = clusterName;
        this.clueterInfo = clueterInfo;
    }
}
