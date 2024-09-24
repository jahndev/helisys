package io.sisin.sisin.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    private String secretKey = "your_secret_key"; // Use a complex secret key

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
}
