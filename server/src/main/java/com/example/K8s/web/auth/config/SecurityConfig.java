package com.example.K8s.web.auth.config;

import com.example.K8s.web.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    // .csrf().disable().headers().frameOptions().disable()
    // he-console 화면을 사용하기 위해서 해당 옵션들을 disable 한다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                // authorizeRequests
                // URL별 권한 관리를 설정하는 옵션의 시작점이다. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                .authorizeRequests()
                // antMatchers
                // 권한 관리 대상을 지정하는 옵션이다.
                // URL, HTTP 메소드별로 관리가 가능하다. 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 관한을 준다.
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console?**").permitAll()
                // "/api/v1/**" 주소를 가진 API는 인증된 권한을 가진 사람만 가능하도록 한다.
                .antMatchers("/api/v1/**").authenticated()
                // anyRequest
                // 설정된 값 이외의 나머지 URL들을 나타낸다. authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들(로그인한 사용자들)에게만 허용한다.
                .anyRequest().authenticated()
                .and()
                // .logout().logoutSuccessUrl("/")
                // 로그아웃 기능에 대한 여러 설정의 진입점이다. 로그아웃 성공시 "/" 주소로 이동한다.
                .logout()
                .logoutSuccessUrl("/")
                .and()
                // oauth2Login
                // OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
                .oauth2Login()
                // userInfoEndpoint
                // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당한다.
                .userInfoEndpoint()
                // userService
                // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.
                // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있다.
                .userService(customOAuth2UserService);
    }

}