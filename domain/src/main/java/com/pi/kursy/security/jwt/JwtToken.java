package com.pi.kursy.security.jwt;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.Builder;

public interface JwtToken {

    JwtData getJwtData();
    String getEncodedToken();
    boolean isValid();
    boolean isExpired();
    boolean isRefreshExpired();
    void invalidateToken();

    @Builder
    record JwtData(String id, String username, RoleEnum role) {}
}
