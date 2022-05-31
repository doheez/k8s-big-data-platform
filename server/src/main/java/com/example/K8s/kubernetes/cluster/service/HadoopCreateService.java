package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.CR.hadoopcr.HadoopCr;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.ClusterMember;
import com.example.K8s.kubernetes.cluster.model.Hadoop;
import com.example.K8s.kubernetes.cluster.repository.ClusterMemberRepository;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.HadoopRepository;
import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.entity.User;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.util.ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HadoopCreateService {

    private final UserRepository userRepository;
    private final ClusterRepository clusterRepository;
    private final HadoopRepository hadoopRepository;
    private final ClusterMemberRepository clusterMemberRepository;

    // hadoop 클러스터 생성
    @Transactional
    public boolean createHadoopCluster(ClusterRegDto regDto) throws IOException {
        // 이름 중복 체크
        boolean exist = clusterRepository.existsByName(regDto.getName());
        if (exist) return false;

        // 클러스터 생성
        Optional<User> user = userRepository.findById(regDto.getId());
        Cluster newCluster = new Cluster(regDto);
        ClusterMember clusterMember = new ClusterMember(newCluster,user.get());
        clusterMemberRepository.save(clusterMember);

        newCluster.setNamespace("hadoop");
        boolean success = callAPICreateHadoopCluster(newCluster);
        if (!success) return false;
        clusterRepository.save(newCluster);

        // hadoop 객체 생성
        Hadoop hadoop = new Hadoop(newCluster);
        hadoopRepository.save(hadoop);
        return true;
    }

    // hadoop 클러스터 생성하는 쿠버네티스 API 호출
    public boolean callAPICreateHadoopCluster(Cluster cluster) throws IOException {
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
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return false;
        }
    }
}