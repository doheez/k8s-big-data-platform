package com.example.K8s.kubernetes.cluster.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JoinUserListDto {
     private List<Long> users;
     private String clusterName;
}