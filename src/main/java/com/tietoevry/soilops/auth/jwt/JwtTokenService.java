package com.tietoevry.soilops.auth.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.tietoevry.soilops.model.Roles;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    public static final long JWT_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Generate token for user
     *
     * @return the user details as a signed JWT token
     */
    public String generateUserToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .claim("roles", Collections.singletonList(Roles.USER))
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateThingToken(String username, String uuid) {
        Instant instant = LocalDateTime.of(2100, 4, 1, 0, 0).atZone(ZoneId.systemDefault()).toInstant();

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
                .claim("roles", Collections.singletonList(Roles.THING))
                .claim("thing", uuid)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(instant))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validates token
     */
    public Claims validateToken(String token) throws JwtException {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claimsJws.getBody();
    }
}