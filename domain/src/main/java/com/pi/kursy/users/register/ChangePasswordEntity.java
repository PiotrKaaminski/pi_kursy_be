package com.pi.kursy.users.register;

import com.pi.kursy.users.shared.UserStatus;

class ChangePasswordEntity {

    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    private final Long id;
    private String password;
    private UserStatus status;

    private final String newPassword;

    ChangePasswordEntity(PasswordValidator passwordValidator, PasswordEncoder passwordEncoder, Long id, String password, UserStatus status, String newPassword) {
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
        this.id = id;
        this.password = password;
        this.status = status;
        this.newPassword = newPassword;
    }
}
