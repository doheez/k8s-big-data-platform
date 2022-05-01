package com.example.K8s.kubernetes.cluster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "hadoop")
public class Hadoop extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double volume;

    @ManyToOne
    @JoinColumn
    private Cluster hadoopCluster;

    // 연관관계 편의 메서드
    public void setCluster(Cluster cluster) {
        if (this.hadoopCluster != null) {
            this.hadoopCluster.removeHadoop(this);
        }
        this.hadoopCluster = cluster;
        cluster.addHadoop(this);
    }

    public Hadoop(Cluster cluster) {
        this.volume = 0;
        setCluster(cluster);
    }
}
