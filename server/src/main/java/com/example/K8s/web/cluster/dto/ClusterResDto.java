package com.example.K8s.web.cluster.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ClusterResDto implements Serializable {
    private Long id;
    private String name;
    private int amount;
    private int type;

}
