package com.example.K8s.web.auth.service;

import com.example.K8s.web.auth.dto.UserInfoDto;
import com.example.K8s.web.auth.dto.UserJoinReqDto;
import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public Long join(UserJoinReqDto userJoinReqDto) {
        User user = new User(userJoinReqDto);
        validateDuplicateUser(user);
        user.encodePassword(passwordEncoder);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        User findUsers = userRepository.findByEmail(user.getEmail());
        if (findUsers != null) {
            throw new IllegalStateException("일치하는 아이디가 존재합니다.");
        }
    }
    public User selectUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }
}
