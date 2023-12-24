package dev.rvz.boxvideos.adapters.configurations.securities;

import dev.rvz.boxvideos.adapters.configurations.securities.filters.AuthenticationFilter;
import dev.rvz.boxvideos.adapters.configurations.securities.filters.UserExistsToVerifyAuthenticationFilter;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetAuthoryToToken;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.GetJWTGenerateToken;
import dev.rvz.boxvideos.adapters.configurations.securities.tokens.TokenIsValid;
import dev.rvz.boxvideos.adapters.outbound.adapter.user.AuthenticationByUserAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityAccessConfiguration {

    private final AuthenticationByUserAdapter authenticationByUserAdapter;
    private final GetJWTGenerateToken getJWTGenerateToken;
    private final GetAuthoryToToken getAuthoryToToken;
    private final TokenIsValid tokenIsValid;

    public SecurityAccessConfiguration(AuthenticationByUserAdapter authenticationByUserAdapter, GetJWTGenerateToken getJWTGenerateToken, GetAuthoryToToken getAuthoryToToken, TokenIsValid tokenIsValid) {
        this.authenticationByUserAdapter = authenticationByUserAdapter;
        this.getJWTGenerateToken = getJWTGenerateToken;
        this.getAuthoryToToken = getAuthoryToToken;
        this.tokenIsValid = tokenIsValid;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authenticationByUserAdapter)
                .passwordEncoder(getPbkdf2PasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/videos/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/videos/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/videos/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/videos/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/videos/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/categories/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/categories/**").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/categories/**").hasAnyAuthority("ADMIN", "USER")
                        .anyRequest()
                        .authenticated())
                .authenticationManager(authenticationManager)
                .addFilterBefore(new UserExistsToVerifyAuthenticationFilter(authenticationManager, getJWTGenerateToken), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(tokenIsValid, getAuthoryToToken), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    Pbkdf2PasswordEncoder getPbkdf2PasswordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


}
