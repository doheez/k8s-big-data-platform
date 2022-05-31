package com.example.K8s.web.cluster.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddUserReqDto {
    String clusterName;
    List<String> emails;
}
