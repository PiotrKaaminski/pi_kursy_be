package com.pi.kursy.security.authentication.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AuthenticationManagerAdapter implements AuthenticationManager {

    private final org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @Override
    public void authenticate(LoginDto login) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.username(),
                    login.password())
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
    }
}
