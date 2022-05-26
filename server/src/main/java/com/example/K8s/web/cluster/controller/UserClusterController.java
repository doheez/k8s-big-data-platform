package com.example.K8s.web.cluster.controller;

import com.example.K8s.web.auth.dto.ErrorResponse;
import com.example.K8s.web.auth.service.UserService;
import com.example.K8s.web.auth.token.JwtTokenProvider;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserClusterController {
    private final UserService userService;
    private final UserClusterService userClusterService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    @PostMapping("/api/create/cluster")
    public ResponseEntity<?> createCluster( @RequestHeader(value = "Authorization")String token,
            @RequestBody ClusterReqDto clusterReqDto){
        ClusterResDto clusterResDto = new ClusterResDto();
        if(!jwtTokenProvider.validateToken(token)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt",null, LocaleContextHolder.getLocale())));
        }
        else {
            Long id = jwtTokenProvider.getId(token);
            User user  = userService.selectUser(id);
            clusterResDto.setUserId(user.getId());
        }
        clusterResDto.setAmount(clusterReqDto.getAmount());
        clusterResDto.setName(clusterReqDto.getName());
       switch( clusterReqDto.getType()){
           case "spark" :
               clusterResDto.setType(1);
               break;
           case "hadoop" :
               System.out.println("hadoop");
               clusterResDto.setType(0);
               break;
           default:
               return ResponseEntity
                       .status(HttpStatus.BAD_REQUEST).body(null);
       }

        log.info("clusterResDto : {}",clusterResDto);
        int value = userClusterService.reqClusterCreate(clusterResDto);
       if(value == -1) {
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(null);
       }
       return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

}
