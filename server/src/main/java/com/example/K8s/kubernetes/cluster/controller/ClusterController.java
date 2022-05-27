package com.example.K8s.kubernetes.cluster.controller;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.dto.PodDetailDto;
import com.example.K8s.kubernetes.cluster.dto.PodDetailRequestDto;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.service.HadoopAdjustService;
import com.example.K8s.kubernetes.cluster.service.HadoopCreateService;

import com.example.K8s.kubernetes.cluster.service.PodDetailService;
import com.example.K8s.kubernetes.cluster.service.SparkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/kubernetes/cluster")
@RequiredArgsConstructor
public class ClusterController {
    private final SparkService sparkService;
    private final HadoopCreateService hadoopCreateService;
    private final HadoopAdjustService hadoopAdjustService;
    private final PodDetailService podDetailService;
    private final ClusterRepository clusterRepository;

    // 클러스터 생성
    @PostMapping
    public String createCluster(ClusterRegDto regDto) throws IOException {
        if (regDto.getType() == 0) {
            boolean result = hadoopCreateService.createHadoopCluster(regDto);
            if (!result) return "hadoop cluster 생성 실패";
        }
        else if (regDto.getType() == 1) {
            boolean result = sparkService.createSparkCluster(regDto);
            if (!result) return "spark cluster 생성 실패";
        }
        return "생성 성공";
    }

    // 클러스터 조절
    @PostMapping("/adj")
    public String modifyCluster(ClusterRegDto adjDto) throws IOException{
        if(adjDto.getType() == 0){
            boolean result = hadoopAdjustService.adjustHadoopCluster(adjDto);
            if (!result) return "hadoop cluster 조절 실패";
        }
        else if(adjDto.getType()==1){
            boolean result = sparkService.adjustSparkCluster(adjDto);
            if (!result) return "spark cluster 조절 실패";
        }
        return "수정 성공";
    }


    // 클러스터 세부 정보
    @GetMapping("/detail")
    public PodDetailDto podDetail(PodDetailRequestDto podDetailRequestDto) throws IOException {
        PodDetailDto detailDto = podDetailService.getPodInfo(podDetailRequestDto);
        return detailDto;
    }
}
