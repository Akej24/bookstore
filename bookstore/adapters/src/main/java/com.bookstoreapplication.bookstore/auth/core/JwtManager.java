package com.bookstoreapplication.bookstore.auth.core;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
class JwtManager {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generateJwt(UserDetails userDetails) {
        Claims claims = Jwts.claims();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("roles", authorities);

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()));
    }

    String getUsernameFromToken(String token) {
       return extractAllClaims(token).getSubject();
    }

    List<GrantedAuthority> getUserRoleFromToken(String token) {
        Collection<?> roles = extractAllClaims(token).get("roles", Collection.class);
        return roles.stream()
                .filter(GrantedAuthority.class::isInstance)
                .map(GrantedAuthority.class::cast)
                .collect(Collectors.toList());
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
