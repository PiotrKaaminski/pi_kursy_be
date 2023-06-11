package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
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

    RegisterUserSnapshot register() throws Exception {
        validateUsername();
        validatePassword();
        validateRole();

        fillMissingValues();

        return toSnapshot();
    }

    private void validateUsername() throws Exception {
        if (!StringUtils.hasText(username)) {
            throw new Exception("username cannot be empty");
        }
        if (StringUtils.containsWhitespace(username)) {
            throw new Exception("username cannot contain whitespace");
        }
        if (username.length() < 3) {
            throw new Exception("username cannot have less than 5 characters");
        }
        if (username.length() > 30) {
            throw new Exception("username cannot have more than 30 characters");
        }
        if (repository.existsByUsername(username)) {
            throw new Exception("username already used");
        }
    }
    private void validatePassword() throws Exception {
        if (passwordValidator.isInvalid(password)) {
            throw new Exception("password is invalid");
        }
    }
    private void validateRole() throws Exception {
        if (role == null) {
            throw new Exception("role cannot be empty");
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
}
