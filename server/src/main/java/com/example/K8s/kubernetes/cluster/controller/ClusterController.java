package com.example.K8s.kubernetes.cluster.controller;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.kubernetes.cluster.service.SparkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequestMapping("/kubernetes/cluster")
@RequiredArgsConstructor
@RestController
public class ClusterController {
    private final SparkService sparkService;
    private final HadoopService hadoopService;
    private final ClusterRepository clusterRepository;

    // 클러스터 생성
    @PostMapping
    public String createCluster(ClusterRegDto regDto) {
        if (regDto.getType() == 0) {
            hadoopService.createHadoopCluster(regDto);
            log.info("hadoop cluster 생성 완료");
            return "hadoop cluster 생성 완료";
        }
        else if (regDto.getType() == 1) {
            sparkService.createSparkCluster(regDto);
            log.info("spark cluster 생성 완료");
            return "spark cluster 생성 완료";
        }

        log.error("클러스터 생성 실패");
        return "생성 실패";
    }
}
