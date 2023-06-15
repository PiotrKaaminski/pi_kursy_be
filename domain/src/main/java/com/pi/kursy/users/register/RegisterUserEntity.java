package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.shared.GenericException;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

class RegisterUserEntity {


    private final RegisterUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    private String id;
    private final String username;
    private String password;
    private final RoleEnum role;
    private ZonedDateTime creationDate;

    private RegisterUserEntity(RegisterUserRepository repository, PasswordEncoder passwordEncoder, PasswordValidator passwordValidator, String username, String password, RoleEnum role) {
        Objects.requireNonNull(repository, "RegisterUserRepository cannot be null");
        Objects.requireNonNull(passwordEncoder, "PasswordEncoder cannot be null");
        Objects.requireNonNull(passwordValidator, "PasswordValidator cannot be null");

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    RegisterUserSnapshot register() throws UserValidationException {
        validateUsername();
        validatePassword();
        validateRole();

        fillMissingValues();

        return toSnapshot();
    }

    private void validateUsername() throws UserValidationException {
        if (!StringUtils.hasText(username)) {
            throw new UserValidationException("username cannot be empty", UserValidationException.Status.USERNAME_EMPTY);
        }
        if (StringUtils.containsWhitespace(username)) {
            throw new UserValidationException("username cannot contain whitespace", UserValidationException.Status.USERNAME_CONTAINS_WHITESPACE);
        }
        if (username.length() < 3) {
            throw new UserValidationException("username cannot have less than 5 characters", UserValidationException.Status.USERNAME_TOO_SHORT);
        }
        if (username.length() > 30) {
            throw new UserValidationException("username cannot have more than 30 characters", UserValidationException.Status.USERNAME_TOO_LONG);
        }
        if (repository.existsByUsername(username)) {
            throw new UserValidationException("username already used", UserValidationException.Status.USERNAME_NON_UNIQUE);
        }
    }
    private void validatePassword() throws UserValidationException {
        if (passwordValidator.isInvalid(password)) {
            throw new UserValidationException("password is invalid", UserValidationException.Status.PASSWORD_INVALID);
        }
    }
    private void validateRole() throws UserValidationException {
        if (role == null) {
            throw new UserValidationException("role cannot be empty", UserValidationException.Status.ROLE_EMPTY);
        }
    }

    private void fillMissingValues() {
        this.id = UUID.randomUUID().toString();
        this.password = passwordEncoder.encode(this.password);
        this.creationDate = ZonedDateTime.now();
    }

    private RegisterUserSnapshot toSnapshot() {
        return new RegisterUserSnapshot(
                id,
                username,
                password,
                role,
                creationDate);
    }

    static class Builder {
        private RegisterUserRepository repository;
        private PasswordEncoder passwordEncoder;
        private PasswordValidator passwordValidator;

        private String username;
        private String password;
        private RoleEnum role;

        Builder repository(RegisterUserRepository repository) {
            this.repository = repository;
            return this;
        }

        Builder passwordEncoder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            return this;
        }

        Builder passwordValidator(PasswordValidator passwordValidator) {
            this.passwordValidator = passwordValidator;
            return this;
        }

        Builder username(String username) {
            this.username = username;
            return this;
        }

        Builder password(String password) {
            this.password = password;
            return this;
        }

        Builder role(RoleEnum role) {
            this.role = role;
            return this;
        }

        RegisterUserEntity build() {
            return new RegisterUserEntity(repository, passwordEncoder, passwordValidator, username, password, role);
        }
    }

    static class UserValidationException extends GenericException {

        private final Status status;

        UserValidationException(String message, Status status) {
            super(message);
            this.status = status;
        }

        public String getStatus() {
            return status.name();
        }

        enum Status {
            USERNAME_EMPTY, USERNAME_CONTAINS_WHITESPACE, USERNAME_TOO_SHORT, USERNAME_TOO_LONG, USERNAME_NON_UNIQUE,
            PASSWORD_INVALID,
            ROLE_EMPTY

        }
    }

}
