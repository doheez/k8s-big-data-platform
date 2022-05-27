package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.dto.PodDetailDto;
import com.example.K8s.kubernetes.cluster.dto.PodDetailRequestDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PodDetailService {

    private final ClusterRepository clusterRepository;
    private static final Integer TIME_OUT_VALUE = 180;

    // 클러스터 내 파드의 세부 정보 추출
    public PodDetailDto getPodInfo(PodDetailRequestDto requestDto) throws IOException {
        Cluster cluster = clusterRepository.findClusterByName(requestDto.getClusterName());
        if (cluster == null) return null;

        CoreV1Api coreV1Api = new CoreV1Api(ClientBuilder.standard().build());
        String namespace = cluster.getNamespace();

        try {
            V1PodList listNamespacedPod = coreV1Api.listNamespacedPod(namespace, null, null, null, null, null,
                                                                        Integer.MAX_VALUE, null, null, TIME_OUT_VALUE, Boolean.FALSE);;

            for (V1Pod pod : listNamespacedPod.getItems()) {
                if (pod.getMetadata().getName().equals(requestDto.getPodName()))
                    return new PodDetailDto(pod);
            }
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }
}
