package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.jwt.JwtToken;
import com.pi.kursy.security.shared.RoleEnum;

record UserSnapshot(
        String id,
        String username,
        RoleEnum role) {

    JwtToken.JwtData toJwtData() {
        return JwtToken.JwtData.builder()
                .id(id)
                .username(username)
                .role(role).build();
    }
}
