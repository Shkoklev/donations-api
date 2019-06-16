package com.mk.donations.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationTime}")
    private Long expirationTime;
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;
    @Value("${jwt.headerString}")
    private String headerString;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns Map object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return Map containing token claims extracted from the specified token or null if a token is invalid.
     */
    public Map<String, Object> parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and userAuthorities as additional claims.
     * These properties are taken from the specified Map object.
     * @param claims to be written in the token
     * @return the JWT token
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    /**
     * Clears the 'Bearer ' prefix from the JWT token
     * @param token containing the 'Bearer ' prefix
     * @return the token without the prefix 'Bearer '
     */
    public String clearPrefix(String token) {
        return token.replace(getTokenPrefix(), "");
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public String getHeaderString() {
        return headerString;
    }

}
