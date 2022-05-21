package com.example.K8s.web.auth.controller;

import com.example.K8s.web.auth.dto.SessionUser;
import com.example.K8s.web.auth.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final SessionService sessionService;
    private final HttpSession httpSession;

    @GetMapping( "/api/v1/user")
    public ResponseEntity<?> getUser() {

        SessionUser sessionUser = sessionService.getSession();
        if (sessionUser != null) {
            System.out.println("api 성공 : "+sessionUser.getName());
        } else {
            System.out.println("api : null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(sessionUser);
    }

}



