package com.pi.kursy.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
class JwtConfiguration implements JwtTokenImpl.Configuration {

    private long expirationTime = 30L * 60 * 1000;
    private long refreshExpirationTime = 15L * 60 * 1000;

    void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime * 60 * 1000;
    }

    void setRefreshExpirationTime(long refreshExpirationTime) {
        this.refreshExpirationTime = refreshExpirationTime * 60 * 1000;
    }
}
