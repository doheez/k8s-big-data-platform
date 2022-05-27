package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.hadoopcr.HadoopCr;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.HadoopRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HadoopAdjustService {

    private final ClusterRepository clusterRepository;
    private final HadoopRepository hadoopRepository;

    // hadoop 클러스터 크기 조절
    @Transactional
    public boolean adjustHadoopCluster(ClusterRegDto adjDto) throws IOException {
        Cluster hadoopCluster = clusterRepository.findClusterByName(adjDto.getName());
        if (hadoopCluster == null) return false;

        // 클러스터 크기 조절
        boolean success = callAPIAdjHadoopCluster(hadoopCluster);
        if (!success) return false;

        hadoopCluster.setAmount(adjDto.getAmount());
        return true;
    }

    // hadoop 클러스터 크기 조절하는 쿠버네티스 API 호출
    public boolean callAPIAdjHadoopCluster(Cluster cluster) throws IOException {
        String name = cluster.getName();
        int amount = cluster.getAmount();

        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "alicek106.hadoop";
        String version = "v1alpha1";
        String namespace = "hadoop";
        String plural = "hadoopservices";
        Object body = new HadoopCr(name, amount);
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();

        try {
            Object delete = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, 0, true, null, null, v1DeleteOptions);
            Object create = apiInstance.createNamespacedCustomObject(group, version, namespace, plural, body, null, null, null);
            System.out.println(create);
            return true;
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return false;
        }
    }
}
