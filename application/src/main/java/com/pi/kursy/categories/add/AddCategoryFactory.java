package com.pi.kursy.categories.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddCategoryFactory {

    private final AddCategoryRepository repository;

    AddCategoryEntity create(AddCategoryDto dto) {
        return new AddCategoryEntity.Builder()
                .repository(repository)
                .name(dto.name()).build();
    }
}
