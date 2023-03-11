package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RegisterUserFactory {

    private final RegisterUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    RegisterUserEntity create(RegisterUserDto dto) {
        return new RegisterUserEntity.Builder()
                .repository(repository)
                .passwordEncoder(passwordEncoder)
                .passwordValidator(passwordValidator)
                .username(dto.username())
                .password(dto.password())
                .role(dto.role()).build();
    }
}
