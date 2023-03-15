package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.users.shared.UserStatus;

import java.time.ZonedDateTime;

record RegisterUserSnapshot(
        String id,
        String username,
        String password,
        RoleEnum role,
        UserStatus status,
        ZonedDateTime creationDate
) {
}
