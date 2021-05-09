package br.com.jwt.endpoint;

import br.com.jwt.usecase.CreateJwtSigned;
import br.com.jwt.usecase.ParseTokenJwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final CreateJwtSigned createJwtSigned;
    private final ParseTokenJwt parseTokenJwt;

    @GetMapping(value = "/create-token")
    public String createToken() {
        String token = createJwtSigned.execute();
        log.info("Token: {}", token);
        return token;
    }

    @GetMapping(value = "/parse-token")
    public Jws<Claims> parseToken(@RequestHeader("token") final String token) {
        Jws<Claims> jws = parseTokenJwt.execute(token);
        log.info("Jws: {}", jws);
        return jws;
    }
}
