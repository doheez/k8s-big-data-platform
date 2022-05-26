package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.sparkcr.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SparkModifyService {
    //    public static boolean replaceSparkCluster(ClusterRegDto regDto) throws IOException {
////        boolean exist = clusterRepository.existsByName(regDto.getName());
////        if(!exist) return false;
//
//        // 클러스터 수정
//        Cluster cluster = new Cluster(regDto);
//        boolean success = replace_Spark_Cluster(cluster);
//
//        return true;
//    }
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

    public static void main(String[] args) throws IOException {
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "radanalytics.io";
        String version = "v1";
        String namespace = "spark";
        String plural = "sparkclusters";
        String name = "my-spark-cluster";
        try {
            Object getobject = apiInstance.getNamespacedCustomObject(group, version, namespace, plural, name);
            System.out.println(getobject);
            // 파싱
            LinkedTreeMap<Object,Object> t = (LinkedTreeMap) getobject;
            Object metadata = t.get("metadata");
            LinkedTreeMap<Object,Object> t1 = (LinkedTreeMap) metadata;
            String resourceVersion = t1.get("resourceVersion").toString();
            System.out.println(resourceVersion);

            Object body = resetCluster("my-spark-cluster", 3,resourceVersion);
            Object result = apiInstance.replaceNamespacedCustomObject(group, version, namespace, plural, name, body, null, null);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#replaceNamespacedCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
