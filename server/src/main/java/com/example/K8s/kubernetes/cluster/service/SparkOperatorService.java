package com.example.K8s.kubernetes.cluster.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkOperatorService {

    public static void main(String[] args) throws IOException{

        try {
            // Deployment
            AppsV1Api api = new AppsV1Api(ClientBuilder.standard().build());
            api.createNamespacedDeployment("spark", Spark_operator_deployment() , null, null, null);

            // Service account
            CoreV1Api service_api = new CoreV1Api(ClientBuilder.standard().build());
            service_api.createNamespacedServiceAccount("spark", Spark_Service_Account(),null,null,null);

            // Rolebinding
            RbacAuthorizationV1Api rbacAuthorizationV1Api = new RbacAuthorizationV1Api(ClientBuilder.standard().build());
            rbacAuthorizationV1Api.createNamespacedRoleBinding("spark", Spark_RoleBinding(),null,null,null);

            System.out.println("deployment : " + api);
            System.out.println("service : " + service_api);
            System.out.println("Rolebinding : "+rbacAuthorizationV1Api);

        }catch (ApiException e) {
            System.out.println(e.getResponseBody());
            e.printStackTrace();
        }

    }
    public static V1Deployment Spark_operator_deployment() {
        V1Deployment deploy = new V1Deployment().apiVersion("apps/v1");
        deploy.setKind("Deployment");

        //metadata
        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName("spark-operator");
        meta.setNamespace("spark");
        Map<String, String> currentLabel = new HashMap<>();
        currentLabel.put("app.kubernetes.io/name","spark-operator");
        currentLabel.put("app.kubernetes.io/version","v1.0.3-v1alpha1");
        meta.setLabels(currentLabel);
        deploy.setMetadata(meta);
        //spec
        V1DeploymentSpec spec = new V1DeploymentSpec();
        //replicas
        spec.setReplicas(1);
        //selector
        V1LabelSelector v1LabelSelector = new V1LabelSelector();
        v1LabelSelector.setMatchLabels(currentLabel);
        spec.setSelector(v1LabelSelector);
        //strategy
        V1DeploymentStrategy strategy = new V1DeploymentStrategy();
        strategy.setType("Recreate");
        spec.setStrategy(strategy);
        //template
        V1PodTemplateSpec v1PodTemplateSpec = new V1PodTemplateSpec();
        // metadata
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        //labels
        v1ObjectMeta.setLabels(currentLabel);

        // spec
        V1PodSpec pod_spec = new V1PodSpec();
        pod_spec.setServiceAccountName("spark-operator");
        V1Container v1Container = new V1Container();
        v1Container.setName("spark-operator");
        v1Container.setImage("quay.io/radanalyticsio/spark-operator:latest-released");
        v1Container.setImagePullPolicy("Always");
        List<V1EnvVar> envs = new ArrayList<>();
        V1EnvVar v1EnvVar = new V1EnvVar();
        v1EnvVar.setName("WATCH_NAMESPACE");
        v1EnvVar.value("~");
        envs.add(v1EnvVar);
        v1Container.setEnv(envs);
        List<V1Container> containers = new ArrayList<>();
        containers.add(v1Container);
        pod_spec.setContainers(containers);

        v1PodTemplateSpec.setMetadata(v1ObjectMeta);
        v1PodTemplateSpec.setSpec(pod_spec);
        spec.setTemplate(v1PodTemplateSpec);
        deploy.setSpec(spec);
        return deploy;
    }
    public static V1ServiceAccount Spark_Service_Account(){
        V1ServiceAccount v1 = new V1ServiceAccount();
        v1.setApiVersion("v1");
        v1.kind("ServiceAccount");
        V1ObjectMeta v1Object= new V1ObjectMeta();
        v1Object.setName("spark-operator");
        v1.metadata(v1Object);
        return v1;
    }
    public static V1RoleBinding Spark_RoleBinding(){
        V1RoleBinding v1RoleBinding = new V1RoleBinding();
        V1ObjectMeta objectMeta = new V1ObjectMeta().name("spark-operator-edit-resources");
        v1RoleBinding.setMetadata(objectMeta);
        V1RoleRef v1RoleRef = new V1RoleRef();
        v1RoleRef.setKind("Role");
        v1RoleRef.setName("Cluster-admin");
        v1RoleRef.setApiGroup("rbac.authorization.k8s.io");
        v1RoleBinding.roleRef(v1RoleRef);
        List<V1Subject> v1Subjects = new ArrayList<>();
        V1Subject v1Subject = new V1Subject();
        v1Subject.setKind("ServiceAccount");
        v1Subject.setName("spark-operator");
        v1Subjects.add(v1Subject);
        v1RoleBinding.setSubjects(v1Subjects);

        return v1RoleBinding;
    }


}
