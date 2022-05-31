package com.example.K8s.web.cluster.controller;

import com.example.K8s.web.cluster.dto.*;
import com.example.K8s.web.cluster.service.UserClusterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api/cluster")
@RestController
@RequiredArgsConstructor
public class UserClusterController {
    private final UserClusterService userClusterService;

    @PostMapping("/create")
    public ResponseEntity<?> createCluster( @RequestHeader(value = "Authorization")String token,
                                            @RequestBody ClusterReqDto clusterReqDto){
        ClusterResDto clusterResDto = userClusterService.setClusterResDto(token, clusterReqDto);
        if(clusterResDto.getType() == -1){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("INVALID_JWT_VALUE");
        }
        else if(clusterResDto.getType() == -2){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("INVALID_INPUT_VALUE");
        }
        int value = userClusterService.reqClusterCreate(clusterResDto);
        if(value != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("COULD_NOT_CREATED");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyCluster( @RequestHeader(value = "Authorization")String token,
                                            @RequestBody ClusterReqDto clusterReqDto){
        ClusterResDto clusterResDto = userClusterService.setClusterResDto(token, clusterReqDto);
        if(clusterResDto.getType() == -1){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("INVALID_JWT_VALUE");
        }
        else if(clusterResDto.getType() == -2){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("INVALID_INPUT_VALUE");
        }
        int value = userClusterService.reqClusterModify(clusterResDto);
        if(value != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("COULD_NOT_CREATED");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getClusterInfo(@RequestHeader(value = "Authorization") String token ){
        ClusterInfoReqDto clusterInfoReqDto = new ClusterInfoReqDto();
        Long userId = userClusterService.checkAuth(token);
        if(userId == -1L)
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("INVALID_JWT_VALUE");
        clusterInfoReqDto.setUserId(userId);
        List<ClusterInfoResDto> clusters = userClusterService.reqClusterInfo(clusterInfoReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(clusters);
    }

    @GetMapping("/detail/{clusterName}/{podName}")
    public ResponseEntity<?> getClusterDetail(@RequestHeader(value = "Authorization") String token, @PathVariable String clusterName, @PathVariable String podName){
        Long userId = userClusterService.checkAuth(token);
        if(userId == -1L)
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("INVALID_JWT_VALUE");

        PodDetailReqDto podDetailReqDto = new PodDetailReqDto(clusterName, podName);
        PodDetailResDto podDetailResDto = userClusterService.reqPodDetail(podDetailReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(podDetailResDto);
    }

    @DeleteMapping("/{clusterName}")
    public ResponseEntity<?> delCluster(@RequestHeader(value = "Authorization") String token, @PathVariable String clusterName){
        Long userId = userClusterService.checkAuth(token);
        if(userId == -1L)
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("INVALID_JWT_VALUE");

        userClusterService.reqDelCluster(clusterName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestHeader(value = "Authorization") String token, @RequestBody AddUserReqDto userReqDto){
        Long userId = userClusterService.checkAuth(token);
        if(userId == -1L)
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("INVALID_JWT_VALUE");

        AddUserCheckDto userCheckDto = userClusterService.addUserCheck(userReqDto);
        int result = userClusterService.reqAddUser(userReqDto.getClusterName(), userCheckDto.getValid_userId());

        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).body(userCheckDto.getInvalid_email());
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAIL_ADD_USER");
    }
}