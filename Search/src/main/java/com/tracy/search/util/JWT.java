package com.tracy.search.util;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWT {
    //不公开的密钥
    @Value("${auth.secret_key}")
    private static String secret_key;
    @Value("${auth.token_alive}")
    private static Integer token_alive;

    public static String generate(String username){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() +token_alive );

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    public static Claims parse(String token) {
        return Jwts.parser()
                .setSigningKey(secret_key)
                .parseClaimsJws(token)
                .getBody();
    }
}
