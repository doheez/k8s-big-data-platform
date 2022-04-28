package com.example.K8s.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "spark")
public class Spark {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false, length = 30)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Spark(Long id, int speed, Timestamp timestamp, User user) {
        this.id = id;
        this.speed = speed;
        this.timestamp = timestamp;
        this.user = user;
    }
}
