package com.pi.kursy.users.register;

import com.pi.kursy.users.shared.UserStatus;

record ChangePasswordSnapshot(
        Long id,
        String externalId,
        String password,
        UserStatus status
) {
}
