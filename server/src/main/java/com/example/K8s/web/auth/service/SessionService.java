package com.example.K8s.web.auth.service;

import com.example.K8s.web.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class SessionService {
    private final HttpSession httpSession;

    //세션에 저장된 user값을 들고오는 함수
    public SessionUser getSession(){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        return sessionUser;
    }

}
