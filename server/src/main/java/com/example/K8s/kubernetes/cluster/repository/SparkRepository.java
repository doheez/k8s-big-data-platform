package com.example.K8s.kubernetes.cluster.repository;

import com.example.K8s.kubernetes.cluster.model.Spark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SparkRepository extends JpaRepository<Spark,Long> {
}
