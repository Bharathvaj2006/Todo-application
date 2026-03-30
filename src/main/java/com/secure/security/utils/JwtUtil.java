package com.secure.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="Inba, Gouthem, Guru, Maharaja, Fasith are all toppers";
    private final Key key= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email){
        long EXPIRE = 1000 * 60 * 10;
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token){
        try{
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
