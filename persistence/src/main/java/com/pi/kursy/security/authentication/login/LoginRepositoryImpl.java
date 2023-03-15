package com.pi.kursy.security.authentication.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class LoginRepositoryImpl implements LoginRepository {

    private final UserAuthenticationJpaRepository jpaRepository;

    @Override
    public Optional<UserSnapshot> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(LoginUserEntity::toSnapshot);
    }
}
