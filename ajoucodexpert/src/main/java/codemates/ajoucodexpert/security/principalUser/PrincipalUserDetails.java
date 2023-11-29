package codemates.ajoucodexpert.security.principalUser;

import codemates.ajoucodexpert.domain.Member;
import codemates.ajoucodexpert.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class PrincipalUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    public PrincipalUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static PrincipalUserDetails createUserDetails(Member member, int authorityCode) {
        // authorityCode에 따라 관리자, TA, 학생 권한을 준다.
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(Objects.requireNonNull(Role.valueOf(authorityCode)).toEntity());


        return new PrincipalUserDetails(
                member.getLoginId(),
                member.getPw(),
                authorities
        );
    }


    // 해당 User의 권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        //return this.getUsername();
        return this.username;
    }

    @Override
    public String getPassword() {
        return "{noop}" + password;
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

        // 사이트 내에서 일정 기간안 로그인을 안하면 휴먼계정을 전환을 하도록 하겠다.
        // -> loginDate 타입을 모아놨다가 이 값을 false로 return 해버리면 된다.
        return true;
    }
}
