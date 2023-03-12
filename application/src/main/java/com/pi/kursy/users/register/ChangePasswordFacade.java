package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangePasswordFacade {

    private final ChangePasswordFactory factory;
    private final ChangePasswordRepository repository;

    void changePassword(ChangePasswordDto dto) throws Exception {
        var changePasswordEntity = factory.create(dto);
        var snapshot = changePasswordEntity.changePassword();
        repository.update(snapshot);
    }
}
