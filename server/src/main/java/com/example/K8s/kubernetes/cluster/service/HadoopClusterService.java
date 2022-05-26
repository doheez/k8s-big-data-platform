package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.hadoopcr.HadoopCr;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;

public class HadoopClusterService {
    public static void main(String[] args) throws IOException {
//        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
//        String group = "alicek106.hadoop";
//        String version = "v1alpha1";
//        String namespace = "hadoop";
//        String plural = "hadoopservices";
//        Object body = new HadoopCr("test3", 2);
//
//        try {
//            Object result = apiInstance.createNamespacedCustomObject(group, version, namespace, plural, body, null, null, null);
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Status code: " + e.getCode());
//            System.err.println("Reason: " + e.getResponseBody());
//            System.err.println("Response headers: " + e.getResponseHeaders());
//            e.printStackTrace();
//        }


        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group = "alicek106.hadoop";
        String version = "v1alpha1";
        String namespace = "hadoop";
        String plural = "hadoopservices";
        String name = "example-hadoopservice";
        Object body = new HadoopCr("example-hadoopservice", 5);
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions();

        try {
//            Object result = apiInstance.getNamespacedCustomObject(group, version, namespace, plural, name);
//            Object result = apiInstance.replaceNamespacedCustomObject(group, version, namespace, plural, name, body, null, null);
//            Object result = apiInstance.replaceNamespacedCustomObjectScale(group, version, namespace, plural, name, body, null, null);
//            Object result = apiInstance.patchNamespacedCustomObject(group, version, namespace, plural, name, body, null, null, null);
            Object result = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, 0, true, null, null, v1DeleteOptions);
            System.out.println(result);

            result = apiInstance.createNamespacedCustomObject(group, version, namespace,plural, body, null, null, null);
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

    }
}
