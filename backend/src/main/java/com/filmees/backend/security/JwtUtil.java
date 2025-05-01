package com.filmees.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "FilmES";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    public String generateToken(String email, int tipoUtilizador) {
        return Jwts.builder()
                .setSubject(email)
                .claim("tipoUtilizador", tipoUtilizador)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
        return extractUsername(token).equals(email) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public <T> T extractClaim(String token, String claimName) {
        Claims claims = getClaims(token);
        return (T) claims.get(claimName);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
