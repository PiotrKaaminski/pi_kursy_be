package com.pi.kursy.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenFactory {

    private final JwtTokenImpl.Configuration jwtConfiguration;

    public JwtToken fromUserData(JwtToken.JwtData jwtData) {
        return new JwtTokenImpl(jwtConfiguration, jwtData);
    }

    public JwtToken fromEncodedJwt(String token) {
        return new JwtTokenImpl(jwtConfiguration, token);
    }


}
