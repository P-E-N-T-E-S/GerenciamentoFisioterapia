package com.implantodontia.infraestrutura.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFiltherToken extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFiltherToken.class);

    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthFiltherToken(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getToken(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameToken(jwt);

                UserDetails details = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Erro ao processar o token JWT", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Opcional: Enviar 401 Unauthorized
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            return headerToken.replace("Bearer ", "");
        }
        return null;
    }
}
