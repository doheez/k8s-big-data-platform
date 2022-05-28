package com.example.K8s.kubernetes.cluster.controller;

import com.example.K8s.kubernetes.cluster.dto.*;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.service.*;
import com.example.K8s.kubernetes.cluster.service.HadoopCreateService;
import com.example.K8s.web.auth.repository.UserRepository;
import io.kubernetes.client.openapi.ApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    private final UserRepository userRepository;
    private final HadoopCreateService hadoopCreateService;
    private final HadoopAdjustService hadoopAdjustService;
    private final PodDetailService podDetailService;
    private final ClusterRepository clusterRepository;
    private final PodInfoListService podInfoListService;

    // 클러스터 생성
    @PostMapping
    public String createCluster(@RequestBody ClusterRegDto regDto) throws IOException {
        if (regDto.getType() == 0) {
            boolean result = hadoopCreateService.createHadoopCluster(regDto);
            if (!result) return "hadoop cluster 생성 실패";
        }
        else if (regDto.getType() == 1) {
            boolean result = sparkCreateServcie.createSparkCluster(regDto);
            if (!result) return "spark cluster 생성 실패";
        }
        return "생성 성공";
    }

    // 클러스터 조절
    @PostMapping("/adj")
    public String modifyCluster(@RequestBody ClusterRegDto adjDto) throws IOException{
        if(adjDto.getType() == 0){
            boolean result = hadoopAdjustService.adjustHadoopCluster(adjDto);
            if (!result) return "hadoop cluster 조절 실패";
        }
        else if(adjDto.getType()==1){
            boolean result = sparkAdjustService.adjustSparkCluster(adjDto);
            if (!result) return "spark cluster 조절 실패";
        }
        return "수정 성공";
    }

    // 클러스터 기본 정보들
    @GetMapping("/{userId}")
    public ArrayList<ClusterInfoListDto> podInfoList(@PathVariable Long userId) throws IOException, ApiException {
        ArrayList<ClusterInfoListDto> podinfolist = podInfoListService.getlistPodInfo(userId);
//        for(ClusterInfoListDto cluster : podinfolist){
//            log.info(cluster.getClusterName());
//            for(PodInfoDto pod : cluster.getPods()){
//                log.info(pod.getName()+" ");
//            }
//        }
        return podinfolist;
    }



    // 클러스터 세부 정보
    @GetMapping("/{clusterName}/{podName}")
    public PodDetailDto podDetail(@PathVariable String clusterName, @PathVariable String podName) throws IOException {
        PodDetailRequestDto podDetailRequestDto = new PodDetailRequestDto();
        podDetailRequestDto.setClusterName(clusterName);
        podDetailRequestDto.setPodName(podName);
        PodDetailDto detailDto = podDetailService.getPodInfo(podDetailRequestDto);
        return detailDto;
    }
}