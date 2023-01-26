package dev.rvz.boxvideos.adapters.configurations;

import dev.rvz.boxvideos.adapters.outbound.adapter.user.AuthenticationByUserAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityAccessConfiguration {

    private final AuthenticationByUserAdapter authenticationByUserAdapter;

    public SecurityAccessConfiguration(AuthenticationByUserAdapter authenticationByUserAdapter) {
        this.authenticationByUserAdapter = authenticationByUserAdapter;
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authenticationByUserAdapter)
                .passwordEncoder(getPbkdf2PasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
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
                .authenticated()
                .and()
                .authenticationManager(authenticationManager);


        return http.build();
    }

    @Bean
    Pbkdf2PasswordEncoder getPbkdf2PasswordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
