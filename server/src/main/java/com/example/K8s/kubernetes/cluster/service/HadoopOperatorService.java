package com.example.K8s.kubernetes.cluster.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;
import java.util.*;

public class HadoopOperatorService {
    public static boolean createHadoopOperator() throws IOException {
        try {
            // API 호출 - Deployment 생성
            AppsV1Api appsV1Api = new AppsV1Api(ClientBuilder.standard().build());
            V1Deployment deployment = createDeployment();
            V1Deployment v1Deployment = appsV1Api.createNamespacedDeployment("hadoop", deployment, null, null, null);
            System.out.println("original deployment" + v1Deployment);

            // API 호출 - RoleBinding 생성
            RbacAuthorizationV1Api rbacAuthorizationV1Api = new RbacAuthorizationV1Api(ClientBuilder.standard().build());
            V1RoleBinding roleBinding = createRoleBinding();
            V1RoleBinding v1RoleBinding = rbacAuthorizationV1Api.createNamespacedRoleBinding("hadoop", roleBinding, null, null, null);
            System.out.println("original roleBinding" + v1RoleBinding);

            // API 호출 - Service-Account 생성
            CoreV1Api coreV1Api = new CoreV1Api(ClientBuilder.standard().build());
            V1ServiceAccount serviceAccount = createServiceAccount();
            V1ServiceAccount v1ServiceAccount = coreV1Api.createNamespacedServiceAccount("hadoop", serviceAccount, null, null, null);
            System.out.println("original serviceAccount" + v1ServiceAccount);

            return true;
        }
        catch (ApiException e) {
            System.out.println(e.getResponseBody());
            e.printStackTrace();
            return false;
        }
    }

    // Deployment 생성
    public static V1Deployment createDeployment(){
        V1Deployment deployment = new V1Deployment();

        //spec
        V1DeploymentSpec spec = new V1DeploymentSpec();
        //spec.replicas
        spec.replicas(1);
        //spec.selector
        Map<String, String> map = new HashMap<>();
        map.put("name", "hadoop-operator");
        spec.selector(new V1LabelSelector().matchLabels(map));
        //spec.template
        V1PodTemplateSpec podTemplateSpec = new V1PodTemplateSpec();
        //spec.template.metadata
        podTemplateSpec.metadata(new V1ObjectMeta().labels(map));
        //spec.template.spec
        V1PodSpec podSpec = new V1PodSpec();
        //spec.template.spec.serviceAccountName
        podSpec.serviceAccount("hadoop-operator");
        //spec.template.spec.containers
        V1Container v1Container = new V1Container();
        v1Container.name("hadoop-operator");
        v1Container.image("alicek106/hadoop:2.6.0-operator");
        v1Container.command(new ArrayList<>(List.of("hadoop-operator")));
        v1Container.imagePullPolicy("Always");
        //spec.template.spec.container.env
        V1EnvVar v1EnvVar1 = new V1EnvVar();
        v1EnvVar1.name("WATCH_NAMESPACE");
        v1EnvVar1.valueFrom(new V1EnvVarSource().fieldRef(new V1ObjectFieldSelector().fieldPath("metadata.namespace")));
        V1EnvVar v1EnvVar2 = new V1EnvVar();
        v1EnvVar2.name("POD_NAME");
        v1EnvVar2.valueFrom(new V1EnvVarSource().fieldRef(new V1ObjectFieldSelector().fieldPath("metadata.name")));
        V1EnvVar v1EnvVar3 = new V1EnvVar();
        v1EnvVar3.name("OPERATOR_NAME");
        v1EnvVar3.value("hadoop-operator");
        List<V1EnvVar> v1EnvVarList = new ArrayList<>();
        v1EnvVarList.add(v1EnvVar1);
        v1EnvVarList.add(v1EnvVar2);
        v1EnvVarList.add(v1EnvVar3);
        v1Container.env(v1EnvVarList);
        List<V1Container> v1Containers = new ArrayList<>();
        v1Containers.add(v1Container);
        podSpec.containers(v1Containers);
        podTemplateSpec.spec(podSpec);
        spec.template(podTemplateSpec);

        //deployment 설정
        deployment.apiVersion("apps/v1");
        deployment.kind("Deployment");
        deployment.metadata(new V1ObjectMeta().name("hadoop-operator"));
        deployment.spec(spec);

        return deployment;
    }

    // RoleBinding 생성
    public static V1RoleBinding createRoleBinding(){
        V1RoleBinding roleBinding = new V1RoleBinding();

        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName("hadoop-operator");

        V1Subject subject = new V1Subject();
        subject.kind("ServiceAccount");
        subject.name("hadoop-operator");
        List<V1Subject> subjects = new ArrayList<>();
        subjects.add(subject);

        V1RoleRef roleRef = new V1RoleRef();
        roleRef.kind("Role");
        roleRef.name("hadoop-operator");
        roleRef.apiGroup("rbac.authorization.k8s.io");

        roleBinding.kind("RoleBinding");
        roleBinding.apiVersion("rbac.authorization.k8s.io/v1");
        roleBinding.setMetadata(objectMeta);
        roleBinding.setSubjects(subjects);
        roleBinding.setRoleRef(roleRef);

        return roleBinding;
    }

    // Service Account 생성
    public static V1ServiceAccount createServiceAccount(){
        V1ServiceAccount serviceAccount = new V1ServiceAccount();

        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName("hadoop-operator");

        serviceAccount.apiVersion("v1");
        serviceAccount.kind("ServiceAccount");
        serviceAccount.metadata(objectMeta);

        return serviceAccount;
    }

    public static void main(String[] args) throws IOException {
        createHadoopOperator();
    }
}
