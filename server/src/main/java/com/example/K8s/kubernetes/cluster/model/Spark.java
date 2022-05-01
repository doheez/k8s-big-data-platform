package com.example.K8s.kubernetes.cluster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Spark extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SparkId;

    @Column(nullable = true)
    private double speed;          //스파크 스피드

    @ManyToOne
    @JoinColumn
    private Cluster cluster;

    // 연관관계 편의 메서드
    public void setCluster(Cluster cluster){
        if(this.cluster!=null){
            this.cluster.removeSpark(this);
        }
        this.cluster = cluster;
        cluster.addSpark(this);
    }

    public Spark(double speed, Cluster cluster){
        this.speed = speed;
        setCluster(cluster);

    }
}
