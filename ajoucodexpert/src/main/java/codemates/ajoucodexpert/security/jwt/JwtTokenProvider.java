package codemates.ajoucodexpert.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
    private Key key;

    private final String secret;
    private final long tokenValidityInMilliSeconds;
    private static final String AUTHORITIES_KEY = "auth";


    // secretKey 인코딩
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-in-seconds}") long tokenValidityInSeconds) {
        log.info("secret: {}", secret);
        System.out.println("secret = " + secret);
        this.secret = secret;
        this.tokenValidityInMilliSeconds = tokenValidityInSeconds * 1000;
    }

    // properties 에서 secretKey 를 가져와서 인코딩
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = secret.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성 (Deprecate 예정)
    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date expiryDate = new Date(now + this.tokenValidityInMilliSeconds); // 토큰 유효 기간

        return Jwts.builder()
                .setSubject(authentication.getName()) // 유저 정보를 담는다.
                .claim(AUTHORITIES_KEY, authorities) // 권한 정보를 담는다.
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setExpiration(expiryDate) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS512)  // 사용할 암호화 알고리즘과 signature에 들어갈 key값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회 -> Authentication 객체 반환
    public Authentication getAuthentication(String accessToken) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    // JWT 토큰에서 유저 아이디 추출
    public String getUserIdFromJwt(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return claims.getSubject();
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 비어있습니다.");
        }
        return false;
    }
}
