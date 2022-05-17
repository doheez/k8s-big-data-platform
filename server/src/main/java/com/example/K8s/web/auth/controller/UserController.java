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

    // Model 객체
    // 서버 탬플릿 엔진에서 사용가능한 객체를 저장할 수 있다.
//    @GetMapping("/")
//    public String index(Model model) {
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        // 세션에 저장된 값이 있을 경우만 model에 userName으로 등록한다.
//        if (user != null) {
//            model.addAttribute("userName",user.getName());
//            model.addAttribute("userEmail",user.getEmail());
//        }
//        return "test";
//    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<?> getUser(){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null){
            System.out.println(sessionUser.getEmail());
        }else{
            System.out.println("null입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(sessionUser);
    }
}