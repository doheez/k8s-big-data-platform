package com.example.K8s.web.cluster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddUserResDto {
    private List<Long> users;
    private String clusterName;
}
