package com.pi.kursy.categories.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AddCategoryRepositoryImpl implements AddCategoryRepository {

    private final AddCategoryJpaRepository jpaRepository;

    @Override
    public void save(AddCategorySnapshot snapshot) {
        jpaRepository.save(AddCategoryJpaEntity.fromSnapshot(snapshot));
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}
