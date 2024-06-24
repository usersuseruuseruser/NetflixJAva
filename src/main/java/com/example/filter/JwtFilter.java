package com.example.filter;

import com.example.exceptionHandling.GlobalExceptionHandler;
import com.example.models.Role;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer";
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public static JwtAuthentication generate(Claims claims) {
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setRoles(getRoles(claims));
        jwtAuthentication.setUserId(claims.get("userId", Long.class));
        return jwtAuthentication;
    }

    private static Set<Role> getRoles(Claims claims) {
        List<String> roles = claims.get("role", List.class);
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null) {
            if (jwtProvider.validateAccessToken(token)) {
                Claims claims = jwtProvider.getAcessClaims(token);
                JwtAuthentication jwtAuthentication = generate(claims);
                jwtAuthentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        if (bearer != null && bearer.startsWith(BEARER)) {
            System.out.println(bearer.substring(BEARER.length() + 1));
            return bearer.substring(BEARER.length() + 1);
        }
        return null;
    }
}
