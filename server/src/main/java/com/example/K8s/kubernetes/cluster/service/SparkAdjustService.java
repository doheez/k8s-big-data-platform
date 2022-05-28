package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.sparkcr.*;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.google.gson.internal.LinkedTreeMap;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparkAdjustService {
    private final ClusterRepository clusterRepository;
    @Transactional
    public boolean adjustSparkCluster(ClusterRegDto adjDto) throws IOException{

        // 클러스터 존재 유무 확인
        boolean exist = clusterRepository.existsByName(adjDto.getName());
        if(!exist) return false;

        // 클러스터 개수 수정
        Cluster cluster = clusterRepository.findClusterByName(adjDto.getName());
        boolean success = adjust_API(cluster, adjDto.getAmount());
        if(!success) return false;

        // 클러스터 개수 수정 정보 저장
        cluster.setAmount(adjDto.getAmount());
        return true;
    }
    public boolean adjust_API (Cluster cluster, int replace_amount) throws IOException{
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group ="radanalytics.io";
        String version ="v1";
        String namespace ="spark";
        String plural = "sparkclusters";
        String name = cluster.getName();
        try {
            Object getobject = apiInstance.getNamespacedCustomObject(group, version, namespace, plural, name);
            String resourceVersion = Parsing(getobject);
            Object body = resetCluster(cluster.getName(), replace_amount,resourceVersion);
            Object result = apiInstance.replaceNamespacedCustomObject(group, version, namespace, plural, name, body, null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return true;
    }


    public static SparkCluster resetCluster(String name, int amount, String resourceVersion) {
        SparkCluster sparkCluster = new SparkCluster();
        sparkCluster.setApiVersion("radanalytics.io/v1");
        sparkCluster.setKind("SparkCluster");
        Metadata metadata = new Metadata();
        metadata.setName(name);
        metadata.setResourceVersion(resourceVersion);
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

    public static String Parsing(Object customobject){
        LinkedTreeMap<Object,Object> t = (LinkedTreeMap) customobject;
        Object metadata = t.get("metadata");
        t = (LinkedTreeMap) metadata;
        String resourceVersion = t.get("resourceVersion").toString();
        return resourceVersion;
    }
}
