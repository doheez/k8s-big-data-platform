package com.example.K8s.kubernetes.cluster.repository;

import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.kubernetes.cluster.model.ClusterMember;
import com.example.K8s.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClusterMemberRepository extends JpaRepository<ClusterMember,Long> {
    List<ClusterMember> findClusterMembersByUser(User user);
    List<ClusterMember> findClusterMembersByCluster(Cluster cluster);
}
