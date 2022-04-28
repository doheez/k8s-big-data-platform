package com.example.K8s.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "hadoop")
public class Hadoop {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int volume;

    @Column(nullable = false, length = 30)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Hadoop(Long id, int volume, Timestamp timestamp, User user) {
        this.id = id;
        this.volume = volume;
        this.timestamp = timestamp;
        this.user = user;
    }
}
