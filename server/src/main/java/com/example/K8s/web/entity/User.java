package com.example.K8s.web.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 255)
    private String token;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    //path값 들어감
    @Column(nullable = false, length = 255)
    private String image;

    @Builder
    public User(Long id, String token, String name, String email, String image) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.email = email;
        this.image = image;
    }
}
