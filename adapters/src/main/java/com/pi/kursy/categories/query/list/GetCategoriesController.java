package com.pi.kursy.categories.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
class GetCategoriesController {

    private final GetCategoriesFacade facade;

    @GetMapping
    GetCategoriesResponse getCategories() {
        return GetCategoriesResponse.fromDto(facade.getCategories());
    }

    record GetCategoriesResponse(
            List<GetCategoriesCategoryEntry> rows
    ) {
        static GetCategoriesResponse fromDto(GetCategoriesResponseDto dto) {
            var entries = dto.rows().stream().map(GetCategoriesCategoryEntry::fromDto).toList();
            return new GetCategoriesResponse(entries);
        }
    }

    record GetCategoriesCategoryEntry(
        String id,
        String name
    ) {
        static GetCategoriesCategoryEntry fromDto(GetCategoriesEntryDto dto) {
            return new GetCategoriesCategoryEntry(dto.id(), dto.name());
        }
    }
}
