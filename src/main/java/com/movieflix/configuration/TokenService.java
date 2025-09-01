package com.movieflix.configuration;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.movieflix.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${movieflix.security.secret}")
    private String secret;



    public String gerarToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .withIssuedAt(Instant.now())
                .withIssuer("movieflix")
                .sign(algorithm);
    }

    public Optional<JWTUserData> validarToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT verify = JWT.require(algorithm)
                    .withIssuer("movieflix")
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .id(verify.getClaim("id").asLong())
                    .name(verify.getClaim("name").asString())
                    .email(verify.getSubject())
                    .build());


        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }

    }
}
