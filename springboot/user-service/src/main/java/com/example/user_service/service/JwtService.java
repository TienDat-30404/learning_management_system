package com.example.user_service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessToken.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshToken.expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetUsername(String jwt) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}