package com.pi.kursy.categories.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class GetCategoriesRepositoryImpl implements GetCategoriesRepository {

    private final GetCategoriesJpaRepository jpaRepository;

    @Override
    public List<GetCategoriesEntryDto> getAll() {
        return jpaRepository.findAll().stream().map(GetCategoriesJpaEntity::toDto).toList();
    }
}
