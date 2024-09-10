package dev.pdrotmz.event_4_u.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret.localhost}")
    private String secret;

    public String generateToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("event-4-u")
                    .withSubject(email)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch(JWTCreationException exception) {
            throw  new RuntimeException("Error while creating");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("event-4-u")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException exception) {
            return null;
        }
    }

    public Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
