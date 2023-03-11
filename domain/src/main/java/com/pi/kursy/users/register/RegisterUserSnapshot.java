package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.users.shared.UserStatus;

record RegisterUserSnapshot(
        String externalId,
        String username,
        String password,
        RoleEnum role,
        UserStatus status
) {
}
