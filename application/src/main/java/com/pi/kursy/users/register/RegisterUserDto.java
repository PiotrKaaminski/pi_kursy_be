package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;

record RegisterUserDto(
        String username,
        String password,
        RoleEnum role
) {}
