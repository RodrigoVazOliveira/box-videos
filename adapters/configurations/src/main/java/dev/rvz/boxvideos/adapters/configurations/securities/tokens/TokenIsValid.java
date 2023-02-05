package dev.rvz.boxvideos.adapters.configurations.securities.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenIsValid {
    private final String secretKey;
    private final Logger LOGGER = LoggerFactory.getLogger(TokenIsValid.class);

    public TokenIsValid(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public Boolean execute(String token) {
        try {
            LOGGER.info("decoding token {}", token);
            DecodedJWT decodeToken = JWT.decode(token);
            Algorithm.HMAC512(secretKey).verify(decodeToken);
            LOGGER.info("token is valid");
            return true;
        } catch (JWTDecodeException | SignatureVerificationException ex) {
            LOGGER.error("error in decoding or token not valid. error: {}", ex.getMessage());
            return false;
        }
    }
}
