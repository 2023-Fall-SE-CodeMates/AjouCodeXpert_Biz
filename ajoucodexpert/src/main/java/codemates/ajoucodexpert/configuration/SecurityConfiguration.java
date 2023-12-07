package codemates.ajoucodexpert.configuration;

import codemates.ajoucodexpert.security.jwt.*;
import codemates.ajoucodexpert.security.principalUser.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;


@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final PrincipalUserDetailsService principalDetailsService;
    private final JwtTokenProvider tokenProvider;
    //private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화 제공
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/favicion.io/**", "/.well-known/**");
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://localhost:3000", "http://localhost:8080", "https://localhost:8080", "https://www.kafarmwrestling.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .csrf(CsrfConfigurer::disable)  // csrf 비활성화 -> token 방식이므로
                .formLogin(FormLoginConfigurer::disable)  // form 로그인 비활성화
                .rememberMe(RememberMeConfigurer::disable) // rememberMe 비활성화
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// jwt token으로 인증하므로 세션은 필요 없으므로 생성 안함

                // 인가 설정
                .authorizeHttpRequests(authorize -> // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
                        authorize

                                .requestMatchers("/api/auth/login", "/api/member/signup").permitAll()  // 로그인은 누구나 가능
                                .requestMatchers("/api/*").permitAll()
                                .requestMatchers("/members/**").hasAnyAuthority("ROLE_HOST", "ROLE_ADMIN")
                                .anyRequest().permitAll()
                )

                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                // filter 설정
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)  // jwt 필터 추가
                .userDetailsService(principalDetailsService)

                .logout(configurer -> configurer.clearAuthentication(true))


                .requiresChannel(channel ->
                       channel.anyRequest().requiresSecure())
                .apply(new JwtSecurityConfig(tokenProvider));


        return http.build();
    }
}
