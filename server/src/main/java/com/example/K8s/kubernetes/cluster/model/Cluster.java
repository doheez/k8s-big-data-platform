package com.example.K8s.kubernetes.cluster.model;

import com.example.K8s.kubernetes.cluster.dto.ClusterRegDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cluster")
public class Cluster extends TimeStamped{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    // type 0 - hadoop
    // type 1 - spark
    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String namespace;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true) // mappedBy="cluster"
    private List<ClusterMember> clusterMembers = new ArrayList<>();

    @OneToMany(mappedBy="cluster", orphanRemoval = true)
    private List<Spark> sparks;

    public void addSpark(Spark spark){
        this.sparks.add(spark);
    }
    public void removeSpark(Spark spark){
        this.sparks.remove(spark);
    }

  

    @OneToMany(mappedBy = "hadoopCluster", orphanRemoval = true)
    private List<Hadoop> hadoops;

    // 연관관계 편의 메서드
    public void addHadoop(Hadoop hadoop) {
        this.hadoops.add(hadoop);
    }
    public void removeHadoop(Hadoop hadoop) {
        this.hadoops.remove(hadoop);
    }



    public Cluster(ClusterRegDto regDto) {
        this.name = regDto.getName();
        this.amount = regDto.getAmount();
        this.type = regDto.getType();
        this.hadoops = new ArrayList<>();
        this.sparks = new ArrayList<>();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setNamespace(String namespace){
        this.namespace = namespace;
    }
}
