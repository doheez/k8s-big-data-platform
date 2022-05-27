package com.example.K8s.web.cluster.controller;

import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.web.auth.dto.ErrorResponse;
import com.example.K8s.web.auth.service.UserService;
import com.example.K8s.web.cluster.dto.ClusterInfoReqDto;
import com.example.K8s.web.cluster.dto.ClusterInfoResDto;
import com.example.K8s.web.cluster.dto.ClusterReqDto;
import com.example.K8s.web.cluster.dto.ClusterResDto;
import com.example.K8s.web.cluster.service.UserClusterService;
import com.example.K8s.web.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/cluster")
@RestController
@RequiredArgsConstructor
public class UserClusterController {
    private final UserClusterService userClusterService;
    private final MessageSource messageSource;
    private final UserService userService;

    @PostMapping("/api/create/cluster")
    public ResponseEntity<?> createCluster( @RequestHeader(value = "Authorization")String token,
                                            @RequestBody ClusterReqDto clusterReqDto){
        ClusterResDto clusterResDto = userClusterService.setClusterResDto(token, clusterReqDto);
        if(clusterResDto.getType() == -1){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt",null, LocaleContextHolder.getLocale())));
        }
        else if(clusterResDto.getType() == -2){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        int value = userClusterService.reqClusterCreate(clusterResDto);
        if(value != 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
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
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt",null, LocaleContextHolder.getLocale())));
        }
        else if(clusterResDto.getType() == -2){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
        int value = userClusterService.reqClusterModify(clusterResDto);
        if(value != 1) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
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
                .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt",null, LocaleContextHolder.getLocale())));
        clusterInfoReqDto.setUserId(userId);
        List<ClusterInfoResDto> clusters = userClusterService.reqClusterInfo(clusterInfoReqDto);

        return ResponseEntity.status(HttpStatus.OK).body(clusters);
    }
}