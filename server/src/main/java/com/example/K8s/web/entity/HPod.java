package com.example.K8s.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "hpod")
public class HPod {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, length = 45)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hadoop_id")
    private Hadoop hadoop;

    @Builder
    public HPod(int id, String status, Hadoop hadoop) {
        this.id = id;
        this.status = status;
        this.hadoop = hadoop;
    }
}
