package com.pi.kursy.users.register;

import com.pi.kursy.shared.GenericException;

class ChangePasswordEntity {

    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    private final String id;
    private String password;

    private final String oldPassword;
    private final String newPassword;

    private ChangePasswordEntity(PasswordValidator passwordValidator, PasswordEncoder passwordEncoder, String id, String password, String oldPassword, String newPassword) {
        this.passwordValidator = passwordValidator;
        this.passwordEncoder = passwordEncoder;
        this.id = id;
        this.password = password;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    ChangePasswordSnapshot changePassword() throws ChangePasswordException {
        validatePassword();
        // encode new password and set as current
        encodeNewPassword();
        return toSnapshot();
    }

    private void validatePassword() throws ChangePasswordException {
        if (passwordValidator.isInvalid(newPassword)) {
            throw new ChangePasswordException("password is invalid", ChangePasswordException.Status.PASSWORD_INVALID);
        }
        if (!passwordEncoder.matches(oldPassword, password)) {
            throw new ChangePasswordException("Old password is different than current", ChangePasswordException.Status.PASSWORD_DIFFERENT);
        }

        if (newPassword.equals(oldPassword)) {
            throw new ChangePasswordException("New password is the same as old password", ChangePasswordException.Status.PASSWORD_IDENTICAL);
        }
    }

    private void encodeNewPassword() {
        this.password = passwordEncoder.encode(newPassword);
    }

    private ChangePasswordSnapshot toSnapshot() {
        return new ChangePasswordSnapshot(
                id,
                password
        );
    }

    static class Builder {
        private PasswordEncoder passwordEncoder;
        private PasswordValidator passwordValidator;

        private String id;
        private String password;
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

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder password(String password) {
            this.password = password;
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
            return new ChangePasswordEntity(passwordValidator, passwordEncoder, id, password, oldPassword, newPassword);
        }
    }

    static class ChangePasswordException extends GenericException {

        private final Status status;

        ChangePasswordException(String message, Status status) {
            super(message);
            this.status = status;
        }

        public String getStatus() {
            return status.name();
        }

        enum Status {
            PASSWORD_DIFFERENT, PASSWORD_IDENTICAL, PASSWORD_INVALID

        }
    }
}
