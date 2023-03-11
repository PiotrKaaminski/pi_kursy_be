package com.pi.kursy.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
class JwtConfiguration implements JwtTokenImpl.Configuration {

    private long expirationTime = 30L;
    private long refreshExpirationTime = 15L;

}
