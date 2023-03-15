package com.pi.kursy.categories.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetCategoriesFacade {

    private final GetCategoriesRepository repository;

    GetCategoriesResponseDto getCategories() {
        return new GetCategoriesResponseDto(repository.getAll());
    }
}
