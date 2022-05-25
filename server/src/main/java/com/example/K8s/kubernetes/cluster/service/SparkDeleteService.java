package com.example.K8s.kubernetes.cluster.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1DeleteOptions;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;

public class SparkDeleteService {
    public static void main(String[] args) throws IOException {
        CustomObjectsApi apiInstance = new CustomObjectsApi(ClientBuilder.standard().build());
        String group ="radanalytics.io";
        String version ="v1";
        String namespace ="spark";
        String plural = "sparkclusters";
        String name = "my-spark-cluster";
        Integer gracePeriodSeconds = 1;
        Boolean orphanDependants = true;
        V1DeleteOptions body = new V1DeleteOptions();
        try {
            Object result = apiInstance.deleteNamespacedCustomObject(group, version, namespace, plural, name, gracePeriodSeconds, orphanDependants, null,null,body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#deleteNamespacedCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
