package com.example.K8s.kubernetes.cluster.dto;

import io.kubernetes.client.openapi.models.V1Pod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PodDetailDto {
    private String name;
    private String namespace;
    private String nodeName;
    private String hostIP;
    private String startTime;
    private String status;
    private String podIP;

    public PodDetailDto(V1Pod pod) {
        this.name = pod.getMetadata().getName();
        this.namespace = pod.getMetadata().getNamespace();
        this.nodeName = pod.getSpec().getNodeName();
        this.hostIP = pod.getStatus().getHostIP();
        this.startTime = pod.getStatus().getStartTime().toString();
        this.status = pod.getStatus().getPhase();
        this.podIP = pod.getStatus().getPodIP();
    }
}