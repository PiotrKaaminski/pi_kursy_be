package com.pi.kursy.security.jwt;

import java.util.Date;

interface InvalidTokenCache {
    boolean containsToken(String token);
    void addToken(String token, Date invalidUntil);
}
