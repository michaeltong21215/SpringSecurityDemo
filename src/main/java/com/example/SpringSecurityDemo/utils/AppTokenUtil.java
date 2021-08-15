package com.example.SpringSecurityDemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class AppTokenUtil implements Serializable {
    @Value("app.secret")
    private String secret;
    private static final long EXPIRATION_MILLISECONDS = 5 * 60 * 60 * 3600;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateAppToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return doGenerateAppToken(claims, userDetails.getUsername());
    }

    private String doGenerateAppToken(Map<String, Object> claims, String subject){
        /*
        const data = Base64UrlEncode(header) + '.' + Base64UrlEncode(payload);
        const hashedData = Hash(data, secret); "{ header: ..., payload: ...}"
        const signature = Base64UrlEncode(hashedData);
         */
         return Jwts.
                 builder().
                 setClaims(claims).
                 setSubject(subject).
                 setIssuedAt(new Date(System.currentTimeMillis())).
                 setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLISECONDS)).
                 signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Date getExpirationDateFromAppToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isAppTokenExpired(String token){
        Date expirationDate = getExpirationDateFromAppToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    public Boolean validateAppToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isAppTokenExpired(token);
    }
}
