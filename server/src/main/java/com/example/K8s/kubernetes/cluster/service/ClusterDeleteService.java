package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.controller.ClusterController;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterDeleteService {

    private final ClusterController clusterController;
    private final ClusterRepository clusterRepository;

    // 데이터베이스에서 클러스터 삭제
    public void deleteClusterInDB(String clusterName) throws IOException {
        Cluster cluster = clusterRepository.findClusterByName(clusterName);
        clusterRepository.delete(cluster);

        if (cluster.getType() == 0) callAPIDeleteHadoopCluster(cluster);
        else callAPIDeleteSparkCluster(cluster);
    }

    // 쿠버네티스 엔진에서 Hadoop 클러스터 삭제
    public void callAPIDeleteHadoopCluster(Cluster cluster) throws IOException {
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "alicek106.hadoop";
        String version = "v1alpha1";
        String namespace = cluster.getNamespace();
        String plural = "hadoopservices";
        String name = cluster.getName();
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();

        try {
            Object result = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, 0, true, null, null, v1DeleteOptions);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    // 쿠버네티스 엔진에서 Spark 클러스터 삭제
    public void callAPIDeleteSparkCluster(Cluster cluster) throws IOException {
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group ="radanalytics.io";
        String version ="v1";
        String namespace = cluster.getNamespace();
        String plural = "sparkclusters";
        String name = cluster.getName();
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();

        try {
            Object result = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, 0, true, null,null, v1DeleteOptions);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
