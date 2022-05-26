package com.example.K8s.web.cluster.service;

import com.example.K8s.web.cluster.dto.ClusterResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserClusterService {
    private final RestTemplate restTemplate;

    @Transactional
    public int reqClusterCreate(ClusterResDto clusterResDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster";
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            public boolean hasError(ClientHttpResponse response) throws IOException{
                HttpStatus status =response.getStatusCode();
                return status.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(converter);
        log.info("서비스 함수 호출");
        //.postForObject(요청할 url, 보낼 object, 응답받을 타입)
        String msg = restTemplate.postForObject(url, clusterResDto, String.class);
        if(msg.equals("생성 실패"))
            return -1;
        else return 1;
    }

}
