package com.example.K8s.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "spod")
public class SPod {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, length = 45)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spark_id")
    private Spark spark;

    @Builder
    public SPod(int id, String status, Spark spark) {
        this.id = id;
        this.status = status;
        this.spark = spark;
    }
}
