package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.authentication.logout.LogoutFacade;
import com.pi.kursy.security.jwt.JwtToken;
import com.pi.kursy.security.jwt.JwtTokenFactory;
import com.pi.kursy.shared.GenericException;
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
            throw new LoginException();
        }

        var userSnapshot = repository.findByUsername(dto.username()).orElseThrow(LoginException::new);
        JwtToken token = jwtTokenFactory.fromUserData(userSnapshot.toJwtData());
        return new LoginResponseDto(token.getEncodedToken(), userSnapshot.username(), userSnapshot.role());
    }

    LoginResponseDto refresh(String authorizationHeader) throws LogoutFacade.InvalidTokenException {
        var jwtToken = logoutFacade.logout(authorizationHeader);
        jwtToken = jwtToken.refreshToken();
        return new LoginResponseDto(jwtToken.getEncodedToken(), null, null);
    }

    static class LoginException extends GenericException {

        private final LoginStatus status;

        LoginException() {
            super("Cannot authenticate user");
            this.status = LoginStatus.FORBIDDEN;
        }

        public String getStatus() {
            return status.name();
        }

        enum LoginStatus {
            FORBIDDEN
        }
    }
}
