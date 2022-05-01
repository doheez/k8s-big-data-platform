package com.example.K8s.kubernetes.cluster.repository;

import com.example.K8s.kubernetes.cluster.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    boolean existsByName(String name);
}
