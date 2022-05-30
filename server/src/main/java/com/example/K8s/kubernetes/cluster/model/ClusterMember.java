package com.example.K8s.kubernetes.cluster.model;

import com.example.K8s.web.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ClusterMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cluster_user_id")
    private Long Id;

    // Cluster
    @ManyToOne(targetEntity = Cluster.class, fetch=FetchType.LAZY)
    @JoinColumn(name="cluster_id")
    private Cluster cluster;

    //User
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    // 연관관계 편의 메서드
    public void setCluster(Cluster cluster){
        if(this.cluster!=null){
            this.cluster.getClusterMembers().remove(this);
        }
        this.cluster = cluster;
        cluster.getClusterMembers().add(this);
    }

    public void setUser(User user){
        if(this.user!=null){
            this.user.getClusterMembers().remove(this);
        }
        this.user=user;
        user.getClusterMembers().add(this);
    }

    public ClusterMember(Cluster cluster, User user){
        setCluster(cluster);
        setUser(user);
    }

}
