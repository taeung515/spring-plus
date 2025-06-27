package org.example.expert.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String bearerJwt = request.getHeader("Authorization");
            String jwt = jwtUtil.substringToken(bearerJwt);

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                setSecurityContextHolder(jwt);
            }
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("Could not set user authentication", ex);
            filterChain.doFilter(request, response);
        }

    }

    private void setSecurityContextHolder(String jwt) {
        Claims claims = jwtUtil.extractClaims(jwt);

        long userId = Long.parseLong(claims.getSubject());
        String nickname = claims.get("nickname", String.class);
        String email = claims.get("email", String.class);
        UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

        AuthUser authUser = new AuthUser(userId, nickname, email, userRole);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUser, "", List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()))
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
