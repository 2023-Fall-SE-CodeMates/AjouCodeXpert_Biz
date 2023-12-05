package codemates.ajoucodexpert.security.jwt;

import codemates.ajoucodexpert.security.jwt.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Value("{{jwt.header}}") public static String header;
    @Value("{{jwt.prefix}}") public static String prefix;


    /**
     * JWt 토큰 정보를 SecurityContext에 저장
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     *
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest, header, prefix);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String userId = jwtTokenProvider.getUserIdFromJwt(jwt);
            log.info("Security Context에 'UID = {}' 인증 정보를 저장했습니다, uri: {}", userId, requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        chain.doFilter(request, response);
        // 다음 filter를 실행하거나, 마지막 필터라면 실행 후 resource 반환
    }

    // Request의 헤더에서 token값 반환 "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(
            HttpServletRequest request,
            String header,
            String prefix) {
        String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
