package com.sscn.library.security.filter;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JsonAuthenticationFilter() {
        super();
        this.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {

                return null;
            }
        });
    }

    public JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setAuthenticationManager(authenticationManager);
    }

    protected String obtainPassword(JsonObject obj) {
        return obj.getString(getPasswordParameter());
    }

    protected String obtainUsername(JsonObject obj) {
        return obj.getString(getUsernameParameter());
    }

    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!"application/json".equals(request.getContentType())) {
            // be aware that obtainPassword and obtainUsername in UsernamePasswordAuthenticationFilter
            // have a different method signature
            return super.attemptAuthentication(request, response);
        }


        try (BufferedReader reader = request.getReader()) {

            //json transformation using javax.json.Json
            JsonObject obj = Json.createReader(reader).readObject();

            if (this.postOnly && !request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
            }

            String username = obtainUsername(obj);
            username = (username != null) ? username.trim() : "";
            String password = obtainPassword(obj);
            password = (password != null) ? password : "";

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                    password);
            // Allow subclasses to set the "details" property
            setDetails(request, authRequest);
            System.out.printf("User %s is signing in with password %s%n", username, password);

            return this.getAuthenticationManager().authenticate(authRequest);


//            return httpSecurity.getSharedObject(AuthenticationManager.class).authenticate(authRequest);
        } catch (IOException ex) {
            throw new AuthenticationServiceException("Parsing Request failed", ex);
        }
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
