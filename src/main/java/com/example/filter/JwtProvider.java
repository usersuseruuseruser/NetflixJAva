package com.example.filter;

import com.example.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }
    public String generateAccessToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationTime = now.plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationTime);
        return Jwts.builder()
                .setSubject(user.id.toString())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .compact();
    }

    public String generateRefreshToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationTime = now.plusMonths(1).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationTime);
        return Jwts.builder()
                .setSubject(user.id.toString())
                .setExpiration(accessExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    public Claims getAcessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean validateToken(String token, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (MalformedJwtException e) {
        } catch (SignatureException e) {
        } catch (Exception e) {
        }
        return false;
    }
}
