package com.example.K8s.kubernetes.cluster.model;

import com.example.K8s.web.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "kluster")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 연관관계 편의 메서드
    public void setUser(User user) {
        if (this.user != null) {
            this.user.removeCluster(this);
        }
        this.user = user;
        user.addCluster(this);
    }


}
