package com.pi.kursy.security.authentication.login;

interface AuthenticationManager {

    void authenticate(LoginDto login) throws AuthenticationException;

    class AuthenticationException extends Exception {
        AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
