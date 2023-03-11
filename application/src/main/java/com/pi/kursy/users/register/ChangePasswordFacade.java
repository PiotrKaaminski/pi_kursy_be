package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangePasswordFacade {

    private final ChangePasswordFactory factory;

    void changePassword(ChangePasswordDto dto) throws Exception {
        var changePasswordEntity = factory.create(dto);
    }
}
