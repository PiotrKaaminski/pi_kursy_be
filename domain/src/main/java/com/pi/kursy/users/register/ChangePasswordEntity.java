package com.pi.kursy.users.register;

import com.pi.kursy.users.shared.UserStatus;

class ChangePasswordEntity {

    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    private final Long id;
    private final String externalId;
    private String password;
    private UserStatus status;

    private final String oldPassword;
    private final String newPassword;

    private ChangePasswordEntity(PasswordValidator passwordValidator, PasswordEncoder passwordEncoder, Long id, String externalId, String password, UserStatus status, String oldPassword, String newPassword) {
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
        this.id = id;
        this.externalId = externalId;
        this.password = password;
        this.status = status;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    ChangePasswordSnapshot changePassword() throws Exception {
        // validate new password
        validateNewPassword();
        // if user status is registered, check if old password matches current
        // if user status is registered, check if new password is different from old password
        validateForRegisteredStatus();
        // if user staus is PENDING, change to REGISTERED
        finishPendingUserRegistration();
        // encode new password and set as current
        encodeNewPassword();
        return toSnapshot();
    }

    private void validateNewPassword() throws Exception {
        if (passwordValidator.isInvalid(newPassword)) {
            throw new Exception("password is invalid");
        }
    }
    private void validateForRegisteredStatus() throws Exception {
        if (!status.equals(UserStatus.REGISTERED)) return;

        if (!passwordEncoder.matches(oldPassword, password)) {
            throw new Exception("Old password is different than current");
        }

        if (newPassword.equals(oldPassword)) {
            throw new Exception("New password is the same as old password");
        }
    }

    private void finishPendingUserRegistration() {
        if (!status.equals(UserStatus.PENDING)) return;
        status = UserStatus.REGISTERED;
    }

    private void encodeNewPassword() {
        this.password = passwordEncoder.encode(newPassword);
    }

    private ChangePasswordSnapshot toSnapshot() {
        return new ChangePasswordSnapshot(
                id,
                externalId,
                password,
                status
        );
    }

    static class Builder {
        private PasswordEncoder passwordEncoder;
        private PasswordValidator passwordValidator;

        private Long id;
        private String externalId;
        private String password;
        private UserStatus status;
        private String oldPassword;
        private String newPassword;

        Builder passwordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        Builder passwordValidator(PasswordValidator passwordValidator) {
            this.passwordValidator = passwordValidator;
            return this;
        }

        Builder id(Long id) {
            this.id = id;
            return this;
        }

        Builder externalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        Builder password(String password) {
            this.password = password;
            return this;
        }

        Builder status(UserStatus status) {
            this.status = status;
            return this;
        }

        Builder oldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        Builder newPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        ChangePasswordEntity build() {
            return new ChangePasswordEntity(passwordValidator, passwordEncoder, id, externalId, password, status, oldPassword, newPassword);
        }
    }
}
