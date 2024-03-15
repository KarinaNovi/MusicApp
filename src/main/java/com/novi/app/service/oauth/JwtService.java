package com.novi.app.service.oauth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
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
    public Authentication getAuthentication(String token) {
        String user = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody()
                .getSubject();
        List<SimpleGrantedAuthority> authorities = Arrays.stream(Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token.replace(PREFIX, ""))
                        .getBody()
                        .get(AUTHORITIES_KEY).toString().split(","))
                .toList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        // TODO: check if rights are empty
        logger.info("Incoming user {} has authorities: {}", user, authorities);
        return new UsernamePasswordAuthenticationToken(user, null,
                authorities);
    }
}