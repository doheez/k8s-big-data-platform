package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.hadoopcr.HadoopCr;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;

public class HadoopClusterService {
    public boolean createHadoopCluster(Cluster cluster) throws IOException {
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

//    public static void main(String[] args) throws IOException {
//        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
//        String group = "alicek106.hadoop";
//        String version = "v1alpha1";
//        String namespace = "hadoop";
//        String plural = "hadoopservices";
//        Object body = new HadoopCr();
//
//        try {
//            Object result = apiInstance.createNamespacedCustomObject(group, version, namespace, plural, body, null, null, null);
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling CustomObjectsApi#patchNamespacedCustomObject");
//            System.err.println("Status code: " + e.getCode());
//            System.err.println("Reason: " + e.getResponseBody());
//            System.err.println("Response headers: " + e.getResponseHeaders());
//            e.printStackTrace();
//        }
//    }
}
