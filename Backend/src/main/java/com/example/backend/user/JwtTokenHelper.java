package com.example.backend.user;

import com.example.backend.user.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenHelper {
    private SecretKey hmacShaKey;

    public JwtTokenHelper(@Value("${app.jwt.secret}") String SECRET_KEY) {
        hmacShaKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setIssuer("RMD")
                .setSubject(user.getNetId())
                .claim("id", user.getId())
                .setIssuedAt(new Date())
                .signWith(hmacShaKey) // Use SecretKey set to base64 encoded value of secret key. Just using the string is deprecated.
                .compact();
    }

    // If token is valid the user should already exist, since we don't create tokens with users that don't exist.
    public String parseAccessToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(hmacShaKey)
                    .build()
                    .parseClaimsJws(token);
            return jwt.getBody().getSubject();
        } catch (SignatureException e) {
            throw new InvalidTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
    }
}
