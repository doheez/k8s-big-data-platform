package com.example.K8s.web.auth.token;


import com.example.K8s.web.auth.repository.UserRepository;
import com.example.K8s.web.auth.service.CustomUserDetailsService;
import com.example.K8s.web.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
//@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@Service
public class JwtTokenProvider {

    // 나중에 key 변경
    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효시간 하루
    private long tokenValidTime = 24 * 60 * 60 * 1000L;

//    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성 (parameter List<String> roles 는 사용 안해서 제외)
    public String createToken(String nickname, Long id) {
        Claims claims = Jwts.claims().setSubject(nickname); // JWT payload 에 저장되는 정보단위 (sub)
        claims.put("id", id); // id를 저장. 정보는 key / value 쌍으로 저장된다.
//        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 복호화 해서 username 얻기
    public String getUsernameFromJwt(String jwt) {
        return getClaims(jwt).getBody().getId();
    }

    private Jws<Claims> getClaims(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw ex;
        }
    }

    // JWT 복호화 해서 id 얻기
    public Long getUserIdFromJwt(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return Long.parseLong(String.valueOf(claims.getBody().get("user_id")));
    }

    //    // JWT 복호화 해서 유저 얻기
    public User getUser(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String id = String.valueOf(claims.getBody().get("id"));
        return userRepository.findById(Long.parseLong(id)).orElse(null);
    }

    public Long getId(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String id = String.valueOf(claims.getBody().get("id"));
        return Long.parseLong(id);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        // 이메일이 들어가야 함
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUser(token).getId().toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw ex;
        }catch (Exception e) {
            return false;
        }
    }
}