package com.example.K8s.web.cluster.service;

import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.auth.token.JwtTokenProvider;
import com.example.K8s.web.cluster.dto.*;
import com.example.K8s.web.entity.User;
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
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserClusterService {
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

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

    public int reqClusterModify(ClusterResDto clusterResDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/adj";
        setRestTemplate();
        //.postForObject(요청할 url, 보낼 object, 응답받을 타입)
        String msg = restTemplate.postForObject(url, clusterResDto, String.class);
        if(msg.equals("수정 성공"))
            return 1;
        else return -1;
    }

    public List<ClusterInfoResDto> reqClusterInfo(ClusterInfoReqDto clusterInfoReqDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/" + clusterInfoReqDto.getUserId();

        setRestTemplate();
        ClusterInfoResList response = restTemplate.getForObject(url, ClusterInfoResList.class);
        if(response == null){
            return null;
        }
        List<ClusterInfoResDto> clusters = response.getClusters();
        return clusters;
    }

    public PodDetailResDto reqPodDetail(PodDetailReqDto podDetailReqDto){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/"+ podDetailReqDto.getClusterName() +"/"+ podDetailReqDto.getPodName();
        setRestTemplate();
        PodDetailResDto response = restTemplate.getForObject(url, PodDetailResDto.class);
        return response;
    }

    public void reqDelCluster(String clusterName){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/" + clusterName;
        setRestTemplate();
        restTemplate.delete(url);
        return;
    }

    public int reqAddUser(String clusterName, ArrayList<Long> userId ){
        String url = "http://ec2-52-78-90-149.ap-northeast-2.compute.amazonaws.com:8080/kubernetes/cluster/user";
        setRestTemplate();

        AddUserResDto addUserResDto = new AddUserResDto(userId, clusterName);
        String msg = restTemplate.postForObject(url,addUserResDto, String.class);

        if(msg.equals("추가 성공"))
            return 1;
        else
            return -1;
    }


    public AddUserCheckDto addUserCheck(AddUserReqDto userReqDto){
        List<String> emails = userReqDto.getEmails();
        AddUserCheckDto userCheckDto = new AddUserCheckDto();

        for(String email : emails){
            Long check = checkEmail(email);
            if(check == -1L)
                userCheckDto.getInvalid_email().add(email);
            else
                userCheckDto.getValid_userId().add(check);
        }
        return userCheckDto;
    }

    @Transactional
    public Long checkEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null)
            return -1L;
        else
            return user.getId();
    }

    public ClusterResDto setClusterResDto( String token, ClusterReqDto clusterReqDto) {
        ClusterResDto clusterResDto = new ClusterResDto();
        Long userId = checkAuth(token);
        if(userId == -1L){
            clusterResDto.setType(-1);
            return clusterResDto;
        }
        clusterResDto.setId(userId);
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