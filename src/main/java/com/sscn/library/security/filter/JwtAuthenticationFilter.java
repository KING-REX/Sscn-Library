package com.sscn.library.security.filter;

import com.sscn.library.security.service.JwtService;
import com.sscn.library.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {

//        if(request.getServletPath().contains("/authors")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        boolean hasTokenCookie = false;

//        Cookie[] cookies = request.getCookies();
//        Cookie tokenCookie = null;
//
//        for(Cookie cookie : cookies) {
//            if(cookie.getName().equals("token")) {
//                hasTokenCookie = true;
//                tokenCookie = cookie;
//            }
//        }

        if((authHeader == null || !authHeader.startsWith("Bearer ")) && !hasTokenCookie) {
            System.out.println("You no reach!");
            filterChain.doFilter(request, response);
            return;
        }

//        if(hasTokenCookie)
//            jwt = tokenCookie.getValue();
//        else
            jwt = authHeader.substring(7);


        username = jwtService.extractUsername(jwt);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("User detail authorities: " + userDetails.getAuthorities().toString());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

//                if(!hasTokenCookie) {
//                    Cookie cookie = new Cookie("token", jwt);
//                    cookie.setHttpOnly(true);
//                    cookie.setMaxAge(1000 * 60 * 24);
//                    response.addCookie(cookie);
//                }
//                else {
//                    tokenCookie.setValue(jwt);
//                }
            }
        }
        System.out.println("You reach!");
        filterChain.doFilter(request, response);
    }
}
