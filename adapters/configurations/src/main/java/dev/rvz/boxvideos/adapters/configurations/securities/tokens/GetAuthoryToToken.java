package dev.rvz.boxvideos.adapters.configurations.securities.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetAuthoryToToken {
    public Authentication execute(final String token) {
        final DecodedJWT decodeJWT = JWT.decode(token);
        final String username = decodeJWT.getSubject();
        final List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        getRoles(decodeJWT, simpleGrantedAuthorities);

        return new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
    }

    private static void getRoles(DecodedJWT decodeJWT, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        final Claim roles = decodeJWT.getClaim("roles");
        String[] rolesString = roles.asArray(String.class);
        for (String role : rolesString) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
    }
}
