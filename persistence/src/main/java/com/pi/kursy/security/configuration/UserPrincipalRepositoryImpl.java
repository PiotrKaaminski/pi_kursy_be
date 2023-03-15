package com.pi.kursy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserPrincipalRepositoryImpl implements UserPrincipalRepository {

    private final UserPrincipalJpaRepository jpaRepository;

    @Override
    public Optional<UserPrincipalSnapshot> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(UserPrincipalEntity::toSnapshot);
    }

}
