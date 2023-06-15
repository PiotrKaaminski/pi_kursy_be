package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterUserFacade {

    private final RegisterUserFactory factory;
    private final RegisterUserRepository repository;

    RegisterUserResponseDto registerUser(RegisterUserDto dto) throws RegisterUserEntity.UserValidationException {
        var user = factory.create(dto);
        var snapshot = user.register();
        repository.save(snapshot);
        return new RegisterUserResponseDto(snapshot.id());
    }
}
