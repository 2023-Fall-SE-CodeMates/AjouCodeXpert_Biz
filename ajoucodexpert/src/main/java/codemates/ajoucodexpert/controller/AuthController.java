package codemates.ajoucodexpert.controller;

import codemates.ajoucodexpert.dto.MemberDto;
import codemates.ajoucodexpert.security.jwt.JwtAuthenticationFilter;
import codemates.ajoucodexpert.security.jwt.JwtTokenProvider;
import codemates.ajoucodexpert.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@EnableWebMvc
@Slf4j
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Value("${jwt.header}") String JWT_HEADER;
    @Value("${jwt.prefix}") String JWT_PREFIX;


    public AuthController(JwtTokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authorize(@RequestBody MemberDto.Login loginDto) {
        log.info("로그인 요청 받음 : {}", loginDto.getLoginId());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPw());

        // authenticate 메서드 실행 시 PrincipalDetailsService의 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication); // JWT Token 생성
        // jwt 토큰에서 유저 아이디와 권한 정보를 추출
        String loginId = tokenProvider.getUserIdFromJwt(jwt);
        String roleName = tokenProvider.getAuthentication(jwt).getAuthorities().stream().toList().get(0).getAuthority();
        Integer role = roleName.equals("ROLE_ADMIN") ? 0 : roleName.equals("ROLE_TA") ? 1 : 2;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtAuthenticationFilter.header, JwtAuthenticationFilter.prefix + " " + jwt);
        HashMap<String, Object> res = new HashMap<>(){{
            put("token", jwt);
            put("loginId", loginId);
            put("role", role);
        }};

        return new ResponseEntity<>(res, httpHeaders, HttpStatus.OK);

    }
}
