package com.example.K8s.web.cluster.service;

import com.example.K8s.web.auth.token.JwtTokenProvider;
import com.example.K8s.web.cluster.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserClusterService {
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public int reqClusterCreate(ClusterResDto clusterResDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster";
        setRestTemplate();
        //.postForObject(요청할 url, 보낼 object, 응답받을 타입)
        String msg = restTemplate.postForObject(url, clusterResDto, String.class);
        log.info(msg);
        if(msg.equals("생성 성공"))
            return 1;
        else return -1;
    }

    @Transactional
    public int reqClusterModify(ClusterResDto clusterResDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/adj";
        setRestTemplate();
        //.postForObject(요청할 url, 보낼 object, 응답받을 타입)
        String msg = restTemplate.postForObject(url, clusterResDto, String.class);
        if(msg.equals("수정 성공"))
            return 1;
        else return -1;
    }

    @Transactional
    public List<ClusterInfoResDto> reqClusterInfo(ClusterInfoReqDto clusterInfoReqDto){
        //예시 url
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/info";
        setRestTemplate();
        ClusterInfoResList response = restTemplate.postForObject(url, clusterInfoReqDto, ClusterInfoResList.class);
        if(response == null){
            return null;
        }
        List<ClusterInfoResDto> clusters = response.getClusters();
        return clusters;
    }

    public PodDetailResDto reqPodDetail(PodDetailReqDto podDetailReqDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/detail";
        setRestTemplate();
        PodDetailResDto response = restTemplate.postForObject(url, podDetailReqDto, PodDetailResDto.class);
        return response;
    }
    public ClusterResDto setClusterResDto( String token, ClusterReqDto clusterReqDto) {
        ClusterResDto clusterResDto = new ClusterResDto();
        Long userId = checkAuth(token);
        if(userId == -1L){
            clusterResDto.setType(-1);
            return clusterResDto;
        }
        clusterResDto.setUserId(userId);
        clusterResDto.setAmount(clusterReqDto.getAmount());
        clusterResDto.setName(clusterReqDto.getName());
        switch (clusterReqDto.getType()) {
            case "spark":
                clusterResDto.setType(1);
                break;
            case "hadoop":
                System.out.println("hadoop");
                clusterResDto.setType(0);
                break;
            default:
                clusterResDto.setType(-2);
                return clusterResDto;
        }

        log.info("clusterResDto : {}", clusterResDto);
        return clusterResDto;
    }

    @Transactional
    public Long checkAuth(String token){
        if (!jwtTokenProvider.validateToken(token)) {
            return -1L;
        } else {
            Long id = jwtTokenProvider.getId(token);
            return id;
        }
    }

    public void setRestTemplate(){
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            public boolean hasError(ClientHttpResponse response) throws IOException{
                HttpStatus status =response.getStatusCode();
                return status.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(converter);
    }

}