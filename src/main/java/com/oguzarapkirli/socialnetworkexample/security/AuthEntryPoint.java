package com.oguzarapkirli.socialnetworkexample.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        HashMap<String, String> json = new HashMap<>();
        json.put("message", "Unauthorized Access to proceed this request. Please send a valid token.");
        json.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        json.put("hasError", String.valueOf(true));
        String jsonStr = new ObjectMapper().writeValueAsString(json);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(jsonStr);
    }
}