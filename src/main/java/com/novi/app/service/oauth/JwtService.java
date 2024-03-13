package com.novi.app.service.oauth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

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
    static final long EXPIRATION_TIME = 86400000;
    // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer";

// Generate secret key. Only for demonstration purposes.
// In production, you should read it from the application
// configuration.
// Byte Array should be like this: b5f59337a612a2a7dc07328f3e7d1a04722967c7f06df20a499a7d3f91ff2a7e
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.
            HS256);
    // Generate signed JWT token
    public String getToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() +
                        EXPIRATION_TIME)).signWith(key)
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
}