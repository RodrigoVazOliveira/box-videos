package dev.rvz.boxvideos.adapters.configurations.securities.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GetJWTGenerateToken {
    private final String secretKey;
    private final Long expiration;
    private final Logger LOGGER = LoggerFactory.getLogger(GetJWTGenerateToken.class);

    public GetJWTGenerateToken(@Value("${jwt.secret.key}") String secretKey, @Value("${jwt.time.expiration}") Long expiration) {
        this.secretKey = secretKey;
        this.expiration = expiration;
    }

    public String execute(String username, List<GrantedAuthority> authorities) {
        LOGGER.info("execute - generate token with username: {} and roles {}", username, authorities);
        List<String> roles = new ArrayList<>();
        authorities.forEach(authority -> roles.add(authority.getAuthority()));

        String token = JWT.create()
                .withIssuer(username)
                .withSubject(username)
                .withArrayClaim("roles", roles.toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secretKey));

        LOGGER.info("execute - token {}", token);

        return token;
    }
}
