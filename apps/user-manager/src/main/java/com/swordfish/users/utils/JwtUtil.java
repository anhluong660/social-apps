package com.swordfish.users.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${security.expire-hours}")
    private long EXPIRE_HOURS;

    @Value("${security.jwt-key}")
    private String SECRET_KEY;

    public String generateToken(Long userId) {
        long currentMilli = System.currentTimeMillis();
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(currentMilli))
                .setExpiration(new Date(currentMilli + EXPIRE_HOURS * 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }
}
