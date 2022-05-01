package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.controller.ClusterController;
import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.Hadoop;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.repository.HadoopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HadoopService {

    private final ClusterController clusterController;
    private final ClusterRepository clusterRepository;
    private final HadoopRepository hadoopRepository;

    // hadoop 클러스터 생성
    public boolean createHadoopCluster(ClusterRegDto regDto) {
        // 이름 중복 체크
        boolean exist = clusterRepository.existsByName(regDto.getName());
        if (exist) return false;

        // 클러스터 생성
        Cluster newCluster = new Cluster(regDto);
        clusterRepository.save(newCluster);

        // hadoop 객체 생성
        Hadoop hadoop = new Hadoop(newCluster);
        hadoopRepository.save(hadoop);
        return true;
    }
}
