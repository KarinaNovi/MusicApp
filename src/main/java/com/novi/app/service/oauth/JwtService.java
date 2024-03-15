package com.novi.app.service.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// TODO: spring-boot-starter-oauth2-client dependency and use keycloak
/**
 *
 JWT = xxxxx.yyyyy.zzzzz
 xxxxx - header, which defines the type of token and the hashing algorithm.
 yyyyy - payload, which, typically, in the case of authentication, contains user information.
 zzzzz - signature, which is used to verify that the token hasn’t been changed along the way.

 */
@Component
public class JwtService {
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);
    static final long EXPIRATION_TIME = 86400000;
    // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";

// Generate secret key. Only for demonstration purposes.
// In production, you should read it from the application
// configuration.
// Byte Array should be like this: b5f59337a612a2a7dc07328f3e7d1a04722967c7f06df20a499a7d3f91ff2a7e
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.
            HS256);

    // Generate signed JWT token
    public String getToken(Authentication authentication) {
        logger.info("authentication: {}", authentication);
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() +
                        EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader
                (HttpHeaders.AUTHORIZATION);
        if (token != null) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody();
        logger.info("claims: {}", claims);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        logger.info("authorities: {}", authorities);
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}