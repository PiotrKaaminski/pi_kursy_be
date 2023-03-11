package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.users.shared.UserStatus;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

class RegisterUserEntity {

    private static final List<RoleEnum> PRIVILEGED_ROLES = List.of(RoleEnum.ADMIN, RoleEnum.TEACHER);

    private final RegisterUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    private String externalId;
    private String username;
    private String password;
    private RoleEnum role;
    private UserStatus status;

    RegisterUserEntity(RegisterUserRepository repository, PasswordEncoder passwordEncoder, PasswordValidator passwordValidator, String username, String password, RoleEnum role) {
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
        this.externalId = UUID.randomUUID().toString();
        this.password = passwordEncoder.encode(this.password);
        this.status = isPrivileged() ? UserStatus.PENDING : UserStatus.REGISTERED;

    }

    private RegisterUserSnapshot toSnapshot() {
        return new RegisterUserSnapshot(
                externalId,
                username,
                password,
                role,
                status);
    }

    private boolean isPrivileged() {
        return PRIVILEGED_ROLES.contains(role);
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
