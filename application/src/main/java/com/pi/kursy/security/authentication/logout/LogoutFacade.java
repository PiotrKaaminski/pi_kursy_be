package com.pi.kursy.security.authentication.logout;

import com.pi.kursy.security.jwt.JwtToken;
import com.pi.kursy.security.jwt.JwtTokenFactory;
import com.pi.kursy.shared.GenericException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LogoutFacade {

    private final JwtTokenFactory jwtTokenFactory;

    public JwtToken logout(String authorizationHeader) throws InvalidTokenException {
        validateHeader(authorizationHeader);
        var jwtToken = jwtTokenFactory.fromEncodedJwt(authorizationHeader.substring(7));
        validateToken(jwtToken);
        jwtToken.invalidateToken();
        return jwtToken;
    }

    private void validateHeader(String authorizationHeader) throws InvalidTokenException {
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new InvalidTokenException(
                    InvalidTokenException.TokenError.INVALID_HEADER,
                    "No authorization header passed");
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException(
                    InvalidTokenException.TokenError.INVALID_HEADER,
                    "Header doesn't start with \"Bearer\"");
        }
    }

    private void validateToken(JwtToken jwtToken) throws InvalidTokenException {
        if (!jwtToken.isValid()) {
            throw new InvalidTokenException(
                    InvalidTokenException.TokenError.INVALID_TOKEN,
                    "Passed token is invalid");
        }
        if (jwtToken.isRefreshExpired()) {
            throw new InvalidTokenException(
                    InvalidTokenException.TokenError.EXPIRED_TOKEN,
                    "Passed token is expired");
        }
    }

    public static class InvalidTokenException extends GenericException {
        private final TokenError error;

        InvalidTokenException(TokenError error, String message) {
            super(message);
            this.error = error;
        }

        public String getStatus() {
            return error.name();
        }

        enum TokenError {
            INVALID_HEADER,
            INVALID_TOKEN,
            EXPIRED_TOKEN
        }
    }

}
