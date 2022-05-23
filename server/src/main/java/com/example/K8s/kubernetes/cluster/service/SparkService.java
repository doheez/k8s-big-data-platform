package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.sparkcr.*;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.Spark;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.SparkRepository;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparkService {
    private final ClusterRepository clusterRepository;
    private final SparkRepository sparkRepository;
    // 스파크 생성
    public boolean createSparkCluster(ClusterRegDto regDto) throws IOException {

        boolean exist = clusterRepository.existsByName(regDto.getName());
        if(exist) return false;

        // 클러스터 생성
        Cluster cluster = new Cluster(regDto);
        boolean success = create_Spark_Cluster(cluster);
        if(!success) return false;

        // 클러스터 저장
        clusterRepository.save(cluster);

        // 스파크 클러스터 저장
        Spark spark = new Spark(0,cluster);
        sparkRepository.save(spark);

        return true;
    }

    public SparkCluster setCluster(String name, int amount){
        SparkCluster sparkCluster = new SparkCluster();
        sparkCluster.setApiVersion("radanalytics.io/v1");
        sparkCluster.setKind("SparkCluster");
        Metadata metadata = new Metadata();
        metadata.setName(name);
        sparkCluster.setMetadata(metadata);
        Spec spec = new Spec();
        Worker worker = new Worker();
        worker.setInstances(Integer.toString(amount));
        spec.setWorker(worker);
        Master master = new Master();
        master.setInstances("1");
        spec.setMaster(master);
        sparkCluster.setSpec(spec);
        return sparkCluster;
    }
    public boolean create_Spark_Cluster(Cluster cluster) throws IOException {
        // CRD 등록
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group ="radanalytics.io";
        String version ="v1";
        String namespace ="spark";
        String plural = "sparkclusters";
        Object body = setCluster(cluster.getName(), cluster.getAmount());
        try{
            Object result = apiInstance.createNamespacedCustomObject(group,version,namespace,plural,body,null,null,null);
            System.out.println(result);
        }
        catch(ApiException e){
            System.err.println("Exception when calling CustomObjectsApi#createNamespacedCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return true;
    }
}
