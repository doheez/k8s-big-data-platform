package com.example.K8s.kubernetes.cluster.repository;

import com.example.K8s.kubernetes.cluster.model.Hadoop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HadoopRepository extends JpaRepository<Hadoop, Long> {
}
