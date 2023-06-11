package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ChangePasswordRepositoryImpl implements ChangePasswordRepository {

    private final ChangePasswordJpaRepository jpaRepository;

    @Override
    public Optional<ChangePasswordSnapshot> findById(String id) {
        return jpaRepository.findById(id).map(ChangePasswordJpaEntity::toSnapshot);
    }

    @Override
    public void update(ChangePasswordSnapshot snapshot) {
        jpaRepository.save(ChangePasswordJpaEntity.fromSnapshot(snapshot));
    }
}
