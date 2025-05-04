package com.taskmanager.app.service.impl;

import com.taskmanager.app.model.User;
import com.taskmanager.app.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String base64Secret;
    public String generateToken(UserDetails userDetails){
        User user = (User) userDetails;
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        return Jwts.builder().subject(userDetails.getUsername()).claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600*1000))
                .signWith(getSignInKey())
                .compact();
    }
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600 *1000* 24 *7))
                .signWith(getSignInKey())
                .compact();

    }
    private SecretKey getSignInKey(){
        byte[] key = Decoders.BASE64.decode(base64Secret);
        return Keys.hmacShaKeyFor(key);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);

    }
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }
    private Claims extractAllClaims(String token){
        return  Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)      // bắt SignatureException nếu fake
                .getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        try {
            Claims claims = extractAllClaims(token);       // ở đây sẽ ném nếu signature fake
            String username = claims.getSubject();
            return username.equals(userDetails.getUsername())
                    && !claims.getExpiration().before(new Date());
        } catch (JwtException ex) {
            // signature sai, token malformed, expired… đều văng vào đây
            return false;
        }
    }
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }
}
