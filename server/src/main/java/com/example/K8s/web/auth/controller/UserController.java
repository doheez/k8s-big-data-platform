package com.example.K8s.web.auth.controller;

import com.example.K8s.web.auth.dto.*;
import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.auth.service.SessionService;
import com.example.K8s.web.auth.service.UserService;
import com.example.K8s.web.auth.token.JwtTokenProvider;
import com.example.K8s.web.entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final MessageSource messageSource;

    /* 회원가입 */
    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@RequestBody UserJoinReqDto request,
                                    BindingResult bindingResult) {

        try {
            Long id = userService.join(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource
                            .getMessage("error.same.id", null, LocaleContextHolder.getLocale())));
        }
    }

    /**
     * 로그인 JWT 발급
     * @return
     * 반환 코드 : 200 / 401 / 404
     */
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReqDto userLoginReqDto) {
        User user = userRepository.findByEmail(userLoginReqDto.getEmail());
        System.out.println(userLoginReqDto.getPassword());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getId());
        System.out.println(user.getRole());
        System.out.println(user.getName());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(messageSource.getMessage("error.none.user", null, LocaleContextHolder.getLocale())));
        }

        if (!passwordEncoder.matches(userLoginReqDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(messageSource.getMessage("error.wrong.password", null, LocaleContextHolder.getLocale())));
        }

        String token = jwtTokenProvider.createToken(user.getName(), user.getId());

        return ResponseEntity.ok(new LoginUserResponse(token));
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@RequestHeader(value = "Authorization")String token){
        if(!jwtTokenProvider.validateToken(token)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt",null, LocaleContextHolder.getLocale())));
        }
        else {
            Long id = jwtTokenProvider.getId(token);
            User user = userService.selectUser(id);
            UserInfoDto userInfoDto = new UserInfoDto(user.getName(), user.getEmail());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userInfoDto);
        }
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class LoginUserResponse {
        private String token;
        public LoginUserResponse(String accessToken) {
            this.token = accessToken;
        }
    }

    /* SNS 로그인 */
    @GetMapping( "/api/v1/user")
    public ResponseEntity<?> getUser() {

        UserSessionDto userSessionDto = sessionService.getSession();
        if (userSessionDto != null) {
            System.out.println("api 성공 : "+ userSessionDto.getName());
        } else {
            System.out.println("api : null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userSessionDto);
    }

}



