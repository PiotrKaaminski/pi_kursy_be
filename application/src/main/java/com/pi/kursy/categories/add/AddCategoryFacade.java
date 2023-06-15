package com.pi.kursy.categories.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddCategoryFacade {

    private final AddCategoryRepository repository;
    private final AddCategoryFactory factory;

    AddCategoryResponseDto addCategory(AddCategoryDto dto) throws AddCategoryEntity.CategoryValidationError {
        var entity = factory.create(dto);
        var snapshot = entity.save();
        repository.save(snapshot);
        return new AddCategoryResponseDto(snapshot.id());
    }
}
