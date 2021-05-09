package br.com.jwt.usecase;

import br.com.jwt.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
@Service
public class ParseTokenJwt {

    @Value("${certificate.secret.key}")
    private String secret;

    public Jws<Claims> execute(String jwtString) {
        Jws<Claims> jws = null;
        final Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtString);
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new BusinessException("Error: " + e.getMessage());
        }

        return jws;
    }

}
