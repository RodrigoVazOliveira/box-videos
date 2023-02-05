package dev.rvz.boxvideos.adapters.configurations.securities.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetJWTGenerateToken;
import dev.rvz.boxvideos.core.domain.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserExistsToVerifyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(UserExistsToVerifyAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;
    private final GetJWTGenerateToken getJWTGenerateToken;

    public UserExistsToVerifyAuthenticationFilter(AuthenticationManager authenticationManager, GetJWTGenerateToken getJWTGenerateToken) {
        this.authenticationManager = authenticationManager;
        this.getJWTGenerateToken = getJWTGenerateToken;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LOGGER.info("attemptAuthentication - attempt authentication");
            final ObjectMapper objectMapper = new ObjectMapper();
            final ServletInputStream inputStream = request.getInputStream();
            final User user = objectMapper.readValue(inputStream, User.class);
            final String username = user.email() != null ? user.email() : user.nick();

            final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, user.password());

            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            LOGGER.error("attemptAuthentication - error attempt authentication error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LOGGER.info("successfulAuthentication - authenticated");
        final UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.addAll(userDetails.getAuthorities());

        final String token = getJWTGenerateToken.execute(userDetails.getUsername(), grantedAuthorities);
        LOGGER.info("successfulAuthentication - token {}", token);

        response.addHeader("Authorization", "Bearer " + token);
    }
}
