package com.example.K8s.web.entity;

import com.example.K8s.kubernetes.cluster.model.Cluster;
import com.example.K8s.web.auth.dto.UserJoinReqDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column( length = 255 )
    private String password;

    //path값 들어감
    @Column( length = 255 )
    private String image;

    @OneToMany(mappedBy="user")
    private List<Cluster> clusters;

    // 연관관계 편의 메서드
    public void addCluster(Cluster cluster) {
        this.clusters.add(cluster);
    }

    // 연관관계 편의 메서드
    public void removeCluster(Cluster cluster) {
        this.clusters.remove(cluster);
    }

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
//    @NotNull
    private Role role;

    @Builder
    public User(Long id, String name, String email,String password, String image, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.role = role != null ? role : Role.USER;
        this.clusters = new ArrayList<>();
    }

    @Builder
    public User(UserJoinReqDto userJoinReqDto) {
        this.name = userJoinReqDto.getName();
        this.email = userJoinReqDto.getEmail();
        this.password = userJoinReqDto.getPassword();
        this.role = role != null ? role : Role.USER;
    }

    public User update(String name, String image) {
        this.name = name;
        this.image = image;

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transactional
    public void encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
    }

}
