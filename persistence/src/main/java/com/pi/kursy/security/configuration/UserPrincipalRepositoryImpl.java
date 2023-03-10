package com.pi.kursy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserPrincipalRepositoryImpl implements UserPrincipalRepository {

    private final UserPrincipalJpaRepository jpaRepository;

    @Override
    public Optional<UserPrincipalSnapshot> getByUsername(String username) {
        return jpaRepository.findById(username)
                .map(UserPrincipalEntity::toSnapshot);
    }

}
