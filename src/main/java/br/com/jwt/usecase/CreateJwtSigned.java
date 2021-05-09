package br.com.jwt.usecase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class CreateJwtSigned {

    @Value("${certificate.secret.key}")
    private String secret;

    public String execute() {

        final Key key = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();

        return Jwts.builder()
                .claim("flow", "Test")
                .setSubject("Flow Test")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30L, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

}
