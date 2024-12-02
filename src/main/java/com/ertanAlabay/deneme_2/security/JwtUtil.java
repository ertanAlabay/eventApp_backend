package com.ertanAlabay.deneme_2.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

	private final String SECRET_KEY = Base64.getEncoder().encodeToString("uTKReOq9InlB0RIGBP03e2FALNh8/1AOrc3E3dawEOw=".getBytes()); // Güvenli bir key seçin
    private final long EXPIRATION_TIME = 86400000; // 1 gün

    @SuppressWarnings("deprecation")
	public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @SuppressWarnings("deprecation")
	public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public boolean isTokenValid(String token) {
        try {
            return !extractClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
