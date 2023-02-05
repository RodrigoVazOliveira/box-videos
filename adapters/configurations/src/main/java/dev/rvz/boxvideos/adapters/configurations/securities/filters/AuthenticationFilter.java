package dev.rvz.boxvideos.adapters.configurations.securities.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.TokenIsValid;
import dev.rvz.boxvideos.core.domain.user.exception.ErrorValidationTokenDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class AuthenticationFilter extends OncePerRequestFilter {
    private final TokenIsValid tokenIsValid;

    public AuthenticationFilter(TokenIsValid tokenIsValid) {
        this.tokenIsValid = tokenIsValid;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");
        if (token != null) {
            final String tokenWithoutBearer = token.replace("Bearer  ", "");
            final Boolean tokenIsValid = this.tokenIsValid.execute(tokenWithoutBearer);

            if (tokenIsValid) {
                filterChain.doFilter(request, response);
            }
        } else {
            generateResponseWithError(request, response);
            filterChain.doFilter(request, response);
        }

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
