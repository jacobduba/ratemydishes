package com.example.backend.user;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JwtToken {
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setIssuer("RMD")
                .setSubject(user.getNetId())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY))) // Use key (object) set to base64 encoded value of secret key. Just using the string is deprecated.
                .compact();
    }
}
