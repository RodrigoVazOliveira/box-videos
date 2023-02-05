package dev.rvz.boxvideos.adapters.configurations.securities.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetAuthoryToToken;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.TokenIsValid;
import dev.rvz.boxvideos.core.domain.user.exception.ErrorForbiddenTokenDTO;
import dev.rvz.boxvideos.core.domain.user.exception.ErrorValidationTokenDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class AuthenticationFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final TokenIsValid tokenIsValid;
    private final GetAuthoryToToken getAuthoryToToken;

    public AuthenticationFilter(TokenIsValid tokenIsValid, GetAuthoryToToken getAuthoryToToken) {
        this.tokenIsValid = tokenIsValid;
        this.getAuthoryToToken = getAuthoryToToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("doFilter - request validation token");
        final String token = request.getHeader("Authorization");
        LOGGER.info("doFilter - token {}", token);
        tokenNotNull(request, response, filterChain, token);

    }

    private void tokenNotNull(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String token) throws IOException, ServletException {
        if (token != null) {
            final String tokenWithoutBearer = token.replace("Bearer ", "");
            final Boolean tokenIsValid = this.tokenIsValid.execute(tokenWithoutBearer);

            verifyToken(request, response, filterChain, tokenWithoutBearer, tokenIsValid);
        } else {
            generateResponseWithError(request, response);
            filterChain.doFilter(request, response);
        }
    }

    private void verifyToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String tokenWithoutBearer, Boolean tokenIsValid) throws IOException, ServletException {
        if (tokenIsValid) {
            final Authentication authentication = getAuthoryToToken.execute(tokenWithoutBearer);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } else {
            generateResponseTokenInvalid(request, response);
            filterChain.doFilter(request, response);
        }
    }

    private static void generateResponseTokenInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ErrorForbiddenTokenDTO errorForbiddenTokenDTO = new ErrorForbiddenTokenDTO(request.getServletPath());
        final ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final String responseJson = objectMapper.writeValueAsString(errorForbiddenTokenDTO);
        response.getWriter().write(responseJson);
    }

    private static void generateResponseWithError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final ErrorValidationTokenDTO errorValidationTokenDTO = new ErrorValidationTokenDTO(request.getServletPath());
        final String responseJson = objectMapper.writeValueAsString(errorValidationTokenDTO);
        response.getWriter().write(responseJson);
    }
}
