package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.dto.ClusterAdjDto;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.CR.hadoopcr.HadoopCr;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.Hadoop;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.HadoopRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import io.kubernetes.client.proto.V1;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HadoopService {

    private final ClusterRepository clusterRepository;
    private final HadoopRepository hadoopRepository;

    // hadoop 클러스터 생성
    public boolean createHadoopCluster(ClusterRegDto regDto) throws IOException {
        // 이름 중복 체크
        boolean exist = clusterRepository.existsByName(regDto.getName());
        if (exist) return false;

        // 클러스터 생성
        Cluster newCluster = new Cluster(regDto);
        boolean success = callAPICreateHadoopCluster(newCluster);
        if (!success) return false;
        clusterRepository.save(newCluster);

        // hadoop 객체 생성
        Hadoop hadoop = new Hadoop(newCluster);
        hadoopRepository.save(hadoop);
        return true;
    }

    // hadoop 클러스터 생성하는 쿠버네티스 API 호출
    public boolean callAPICreateHadoopCluster(Cluster cluster) throws IOException {
        String name = cluster.getName();
        int amount = cluster.getAmount();

        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "alicek106.hadoop";
        String version = "v1alpha1";
        String namespace = "hadoop";
        String plural = "hadoopservices";
        Object body = new HadoopCr(name, amount);

        try {
            Object result = apiInstance.createNamespacedCustomObject(group, version, namespace, plural, body, null, null, null);
            System.out.println(result);
            return true;
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#patchNamespacedCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return false;
        }
    }

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
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "alicek106.hadoop";
        String version = "v1alpha1";
        String namespace = "hadoop";
        String plural = "hadoopservices";
        String name = cluster.getName();
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();

        try {
            Object delete = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, 0, true, null, null, v1DeleteOptions);
            callAPICreateHadoopCluster(cluster);
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
