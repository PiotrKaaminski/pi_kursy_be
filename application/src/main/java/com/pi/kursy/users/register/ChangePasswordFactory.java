package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangePasswordFactory {

    private final ChangePasswordRepository repository;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    ChangePasswordEntity create(ChangePasswordDto dto) throws Exception {
        var snapshot = repository.findByExternalId(dto.userId())
                .orElseThrow(() -> new Exception("user with id " + dto.userId() + " doesn't exist"));

        return new ChangePasswordEntity(
                passwordValidator,
                passwordEncoder,
                snapshot.id(),
                snapshot.password(),
                snapshot.status(),
                dto.oldPassword(),
                dto.newPassword()
        );
    }

}
