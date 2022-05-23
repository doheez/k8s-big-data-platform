package com.example.K8s.web.auth.repository;

import com.example.K8s.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//이메일을 통해 이미 생성된 사용자인지 확인하기 위한 메소드
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}