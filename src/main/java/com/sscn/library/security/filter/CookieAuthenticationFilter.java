package com.sscn.library.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieAuthenticationFilter extends OncePerRequestFilter {

    public static final String COOKIE_NAME = "token";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.printf("Cookies is %snull", request.getCookies() != null ? "not " : "");
        response.getHeaderNames().forEach(headerName -> System.out.println("Header: " + response.getHeader(headerName)));

        Stream.of(Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[0])).filter(cookie -> COOKIE_NAME.equals(cookie.getName())).findFirst().ifPresent(cookie -> {
            System.out.println("Cookie value: " + cookie.getValue());
            PreAuthenticatedAuthenticationToken preAuth = new PreAuthenticatedAuthenticationToken(cookie.getValue(), null);
            preAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(preAuth);
        });

        filterChain.doFilter(request, response);
    }
}
