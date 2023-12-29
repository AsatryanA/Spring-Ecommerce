package com.example.springEcommerce.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${secret}")
    private String jwtSecret;

    @Value("${expiration}")
    private long accessTokenExpirationMs;

    public String generateAccessToken(Long userId, String username) {
        var claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date())
                        .getTime() + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getSubject(String token) {
        log.info("get subject");
        return parseJwtClaims(token).getSubject();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public boolean validateJwtTokenSignature(String authToken) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect: " + e.getMessage());
        }
    }

    public Long getId(String token) {
        log.info("get id");
        Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return body.get("userId", Long.class);
    }
}
