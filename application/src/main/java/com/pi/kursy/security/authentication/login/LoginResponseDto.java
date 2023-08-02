package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.shared.RoleEnum;

record LoginResponseDto(
        String token,
        String username,
        RoleEnum role) {
}
