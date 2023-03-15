package com.pi.kursy.users.register;

import com.pi.kursy.users.shared.UserStatus;

record ChangePasswordSnapshot(
        String id,
        String password,
        UserStatus status
) {
}
