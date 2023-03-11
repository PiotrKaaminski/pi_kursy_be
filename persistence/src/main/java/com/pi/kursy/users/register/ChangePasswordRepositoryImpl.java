package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ChangePasswordRepositoryImpl implements ChangePasswordRepository {

    private final ChangePasswordJpaRepository jpaRepository;

    @Override
    public Optional<ChangePasswordSnapshot> findByExternalId(String externalId) {
        return jpaRepository.findByExternalId(externalId).map(ChangePasswordOrmEntity::toSnapshot);
    }

    @Override
    public void update(ChangePasswordSnapshot snapshot) {
        jpaRepository.save(ChangePasswordOrmEntity.fromSnapshot(snapshot));
    }
}
