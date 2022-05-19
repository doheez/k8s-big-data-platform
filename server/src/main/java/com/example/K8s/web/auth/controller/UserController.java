package com.example.K8s.web.auth.controller;

import com.example.K8s.web.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final HttpSession httpSession;

    @GetMapping("/api/v1/user")
    public ResponseEntity<?> getUser() {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser != null) {
            System.out.println(sessionUser.getEmail());
        } else {
            System.out.println("null입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(sessionUser);
    }
}



