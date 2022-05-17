package com.example.K8s.web.entity;

import com.example.K8s.kubernetes.cluster.model.Cluster;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column( length = 255 )
    private String token;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    //path값 들어감
    @Column( length = 255 )
    private String image;

    @OneToMany(mappedBy="user")
    private List<Cluster> clusters;

    // 연관관계 편의 메서드
    public void addCluster(Cluster cluster) {
        this.clusters.add(cluster);
    }

    // 연관관계 편의 메서드
    public void removeCluster(Cluster cluster) {
        this.clusters.remove(cluster);
    }

    private String role = "ROLE_GUEST";


    @Builder
    public User(Long id, String token, String name, String email, String image) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.email = email;
        this.image = image;
    }


    public User update(String name, String image) {
        this.name = name;
        this.image = image;

        return this;
    }

}
