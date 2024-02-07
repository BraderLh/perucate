package com.balh.perucate.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${jwt.password}")
    private String secretToken;

    @Value("${time.expiration.jwt.token}")
    private String timeExpirationToken;

    public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretToken).parseClaimsJws(token).getBody();
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(timeExpirationToken)))  //300 000  60000 + 100 * 60 * 60 * 10
                .signWith(SignatureAlgorithm.HS256, secretToken).compact();
    }

    public String generateToken(String userName, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,userName);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
}
