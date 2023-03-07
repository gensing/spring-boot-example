package com.tensing.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensing.boot.config.properties.TensingProperties;
import com.tensing.boot.security.module.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AppConfiguration {

    private final TensingProperties tensingProperties;

    @Bean
    public TokenProvider accessTokenProvider() {
        TensingProperties.JwtProperties jwt = tensingProperties.jwt();
        TokenProvider tokenProvider = new TokenProvider(jwt.secretKey(), jwt.signatureAlgorithm(), jwt.expirationInMs());
        return tokenProvider;
    }

    @Bean
    public TokenProvider refreshTokenProvider() {
        TensingProperties.JwtProperties jwt = tensingProperties.jwt();
        TokenProvider tokenProvider = new TokenProvider(jwt.refreshSecretKey(), jwt.refreshSignatureAlgorithm(), jwt.refreshExpirationInMs());
        return tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }
}