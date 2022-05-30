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
    private List<CoUserInfoDto> users = new ArrayList<>();
    private List<PodInfoDto> pods = new ArrayList<>();

    public ClusterInfoListDto(int type, String clusterName, List<CoUserInfoDto> users, List<PodInfoDto> pods){
        this.type = type;
        this.clusterName = clusterName;
        this.users = users;
        this.pods = pods;
    }
}
