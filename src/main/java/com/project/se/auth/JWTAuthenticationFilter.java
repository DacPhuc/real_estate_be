package com.project.se.auth;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.se.domain.ApplicationUser;
import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.project.se.auth.SecurityConstant.EXPIRATION_TIME;
import static com.project.se.auth.SecurityConstant.HEADER_STRING;
import static com.project.se.auth.SecurityConstant.SECRET;
import static com.project.se.auth.SecurityConstant.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ApplicationUser applicationUser = new ObjectMapper()
                    .readValue(request.getInputStream(), ApplicationUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            applicationUser.getName(),
                            applicationUser.getPassword(),
                            new ArrayList<>()
                    )
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        System.out.println("Token " + token);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
