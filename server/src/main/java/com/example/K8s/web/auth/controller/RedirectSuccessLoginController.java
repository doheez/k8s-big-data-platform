package com.example.K8s.web.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class RedirectSuccessLoginController {

    @GetMapping("/redirectToMain")
    public RedirectView redirectToMainPage(){
        return new RedirectView("http://localhost:3000/");
    }

}
