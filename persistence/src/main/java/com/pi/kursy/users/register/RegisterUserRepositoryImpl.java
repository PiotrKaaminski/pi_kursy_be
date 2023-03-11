package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class RegisterUserRepositoryImpl implements RegisterUserRepository {

    private final RegisterUserJpaRepository jpaRepository;

    @Override
    public void save(RegisterUserSnapshot snapshot) {
        jpaRepository.save(UserRegisterEntity.fromSnapshot(snapshot));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
