package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.authentication.logout.LogoutFacade;
import com.pi.kursy.security.jwt.JwtToken;
import com.pi.kursy.security.jwt.JwtTokenFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoginFacade {

    private final JwtTokenFactory jwtTokenFactory;
    private final AuthenticationManager authenticationManager;
    private final LoginRepository repository;
    private final LogoutFacade logoutFacade;

    LoginResponseDto login(LoginDto dto) throws LoginException {
        try {
            authenticationManager.authenticate(dto);
        } catch (AuthenticationManager.AuthenticationException e) {
            throw new LoginException(e);
        }

        var userSnapshot = repository.findByUsername(dto.username()).orElseThrow(LoginException::new);
        JwtToken token = jwtTokenFactory.fromUserData(userSnapshot.toJwtData());
        return new LoginResponseDto(token.getEncodedToken());
    }

    LoginResponseDto refresh(String authorizationHeader) throws LogoutFacade.InvalidTokenException {
        var jwtToken = logoutFacade.logout(authorizationHeader);
        jwtToken = jwtToken.refreshToken();
        return new LoginResponseDto(jwtToken.getEncodedToken());
    }

    static class LoginException extends Exception {

        @Getter
        private final LoginStatus status;

        LoginException() {
            this(null);
        }

        LoginException(Throwable cause) {
            super("Cannot authenticate user", cause);
            this.status = LoginStatus.FORBIDDEN;
        }

        enum LoginStatus {
            FORBIDDEN
        }
    }
}
