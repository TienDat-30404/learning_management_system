// package com.example.course_service.util;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.interfaces.DecodedJWT;
// import com.auth0.jwt.interfaces.JWTVerifier;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// @Component
// public class JwtTokenUtil {

//     @Value("${jwt.secret}")
//     private String jwtSecret;

//     private Algorithm getAlgorithm() {
//         return Algorithm.HMAC256(jwtSecret);
//     }

//     private DecodedJWT getDecodedJWT(String token) {
//         JWTVerifier verifier = JWT.require(getAlgorithm()).build();
//         return verifier.verify(token);
//     }

//     public Long extractUserId(String token) {
//         return Long.parseLong(getDecodedJWT(token).getSubject());
//     }


//     public boolean validateToken(String token) {
//         try {
//             getDecodedJWT(token); // Throws exception if invalid
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
// }
