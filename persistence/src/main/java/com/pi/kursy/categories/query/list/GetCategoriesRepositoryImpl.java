package com.pi.kursy.categories.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class GetCategoriesRepositoryImpl implements GetCategoriesRepository {

    private final GetCategoriesJpaRepository jpaRepository;

    @Override
    public List<GetCategoriesEntryDto> getAll() {
        return jpaRepository.findAll().stream().map(CategoryJpaEntity::toDto).toList();
    }
}
