package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.dto.PodInfoDto;
import com.example.K8s.kubernetes.cluster.dto.ClusterInfoListDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.entity.User;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PodInfoListService {
    private final UserRepository userRepository;

    public List<Cluster> getClusters(Long userId){
        Optional<User> user = userRepository.findById(userId);
        List<Cluster> clusters = user.get().getClusters();
        return clusters;
    }

    public ArrayList<ClusterInfoListDto> getlistPodInfo(List<Cluster> clusters) throws IOException, ApiException {
        ArrayList<ClusterInfoListDto> result = new ArrayList<>();
        CoreV1Api coreV1Api = new CoreV1Api(ClientBuilder.standard().build());
        try{
            for(Cluster cluster : clusters){
                String namespace = cluster.getNamespace();
                V1PodList podlist = coreV1Api.listNamespacedPod(namespace,null,null,null,null,null,null,null,null,null,null);

                List<PodInfoDto> podinfos = new ArrayList<>();
                for(V1Pod pod : podlist.getItems()){
                    if (pod.getMetadata().getName().contains(cluster.getName())) {
                        String name = pod.getMetadata().getName();
                        String podIP = pod.getStatus().getPodIP();
                        String status = pod.getStatus().getPhase();
                        PodInfoDto podInfoDto = new PodInfoDto(name,podIP,status);
                        podinfos.add(podInfoDto);
                    }

                }

                ClusterInfoListDto clusterInfoListDto = new ClusterInfoListDto(cluster.getType(),cluster.getName(),podinfos);
                result.add(clusterInfoListDto);
                }
            }
        catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

    return result;
    }

}
