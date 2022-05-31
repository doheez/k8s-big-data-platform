package com.example.K8s.kubernetes.cluster.service;

import com.example.K8s.kubernetes.cluster.dto.JoinUserListDto;
import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.ClusterMember;
import com.example.K8s.kubernetes.cluster.repository.ClusterMemberRepository;
import com.example.K8s.kubernetes.cluster.repository.ClusterRepository;
import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinUserService {
    private final ClusterMemberRepository clusterMemberRepository;
    private final ClusterRepository clusterRepository;
    private final UserRepository userRepository;

    public boolean join_user(JoinUserListDto joinUserList){

        for(Long userId : joinUserList.getUsers()){
            Cluster cluster = clusterRepository.findClusterByName(joinUserList.getClsuterName());
            Optional<User> new_user = userRepository.findById(userId);
            ClusterMember clusterMember = new ClusterMember(cluster,new_user.get());
            clusterMemberRepository.save(clusterMember);
        }
        return true;
    }
}
