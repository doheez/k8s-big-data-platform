package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.Spark;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.SparkRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SparkService {
    private final ClusterRepository clusterRepository;
    private final SparkRepository sparkRepository;
    // 스파크 생성
    public String createSparkCluster(ClusterRegDto clusterRegDto){
        // 클러스터 이름, 갯수 입력 받아서 스파크 파드 생성
        // spark 파드 1개 생성
        // spark 파드 입력 받은 갯수 만큼 생성
        // master랑 slave연결하기

        // 클러스터 저장
        Cluster cluster = new Cluster(clusterRegDto);
        clusterRepository.save(cluster);


        // 스파크 저장
        Spark spark = new Spark(0,cluster);
        sparkRepository.save(spark);

    }

}
