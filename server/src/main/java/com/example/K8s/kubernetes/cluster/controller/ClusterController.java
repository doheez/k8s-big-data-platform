package com.example.K8s.kubernetes.cluster.controller;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.dto.PodInfoDto;
import com.example.K8s.kubernetes.cluster.dto.ClusterInfoListDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.service.HadoopService;

import com.example.K8s.kubernetes.cluster.service.PodInfoListService;
import com.example.K8s.kubernetes.cluster.service.SparkAdjustService;
import com.example.K8s.kubernetes.cluster.service.SparkCreateService;
import com.example.K8s.web.auth.repository.UserRepository;
import io.kubernetes.client.openapi.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kubernetes/cluster")
@RequiredArgsConstructor
public class ClusterController {
    private final SparkCreateService sparkCreateServcie;
    private final SparkAdjustService sparkAdjustService;
    private final HadoopService hadoopService;
    private final UserRepository userRepository;
    private final ClusterRepository clusterRepository;
    private final PodInfoListService podInfoListService;

    // 클러스터 생성
    @PostMapping
    public String createCluster(ClusterRegDto regDto) throws IOException {
        if (regDto.getType() == 0) {
            boolean result = hadoopService.createHadoopCluster(regDto);
            return "hadoop cluster 생성 완료";
        }
        else if (regDto.getType() == 1) {
            boolean result = sparkCreateServcie.createSparkCluster(regDto);
            return "spark cluster 생성 완료";
        }
        return "생성 성공";
    }

    // 클러스터 조절
    @PostMapping("/adj")
    public String modifyCluster(ClusterRegDto adjDto) throws IOException{
        if(adjDto.getType() == 0){
            boolean result = hadoopService.adjustHadoopCluster(adjDto);
            if (!result) return "hadoop cluster 조절 실패";
        }
        else if(adjDto.getType()==1){
            boolean result = sparkAdjustService.adjustSparkCluster(adjDto);
            if (!result) return "spark cluster 조절 실패";
        }
        return "수정 성공";
    }

    // 클러스터 기본 정보들
    @GetMapping
    public ArrayList<ClusterInfoListDto> podInfoList(Long userId) throws IOException, ApiException {
        List<Cluster> clusters = podInfoListService.getClusters(userId);
        ArrayList<ClusterInfoListDto> podinfolist = podInfoListService.getlistPodInfo(clusters);
        return podinfolist;
    }


}
