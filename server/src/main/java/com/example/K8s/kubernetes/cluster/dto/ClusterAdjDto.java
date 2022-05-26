package com.example.K8s.kubernetes.cluster.dto;

import com.example.K8s.web.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ClusterAdjDto {
    private User user;
    private String name;
    private int amount;
    private int type;
}