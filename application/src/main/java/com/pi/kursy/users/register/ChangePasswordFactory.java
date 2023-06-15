package com.pi.kursy.users.register;

import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChangePasswordFactory {

    private final ChangePasswordRepository repository;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    ChangePasswordEntity create(ChangePasswordDto dto) throws CreateEntityException {
        var snapshot = repository.findById(dto.userId())
                .orElseThrow(() -> new CreateEntityException("user with id " + dto.userId() + " doesn't exist"));

        return new ChangePasswordEntity.Builder()
                .passwordValidator(passwordValidator)
                .passwordEncoder(passwordEncoder)
                .id(snapshot.id())
                .password(snapshot.password())
                .oldPassword(dto.oldPassword())
                .newPassword(dto.newPassword()).build();
    }

    static class CreateEntityException extends GenericException {
        CreateEntityException(String message) {
            super(message);
        }

        public String getStatus() {
            return "USER_NOT_FOUND";
        }
    }

}
