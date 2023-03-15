package com.pi.kursy.categories.add;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
class AddCategoryController {

    private final AddCategoryFacade facade;

    @PostMapping
    AddCategoryResponse addCategory(@RequestBody AddCategoryRequest request) throws Exception {
        var responseDto = facade.addCategory(request.toDto());
        return AddCategoryResponse.fromDto(responseDto);
    }

    record AddCategoryRequest(String name) {
        AddCategoryDto toDto() {
            return new AddCategoryDto(name);
        }
    }

    record AddCategoryResponse(String id) {
        static AddCategoryResponse fromDto(AddCategoryResponseDto dto) {
            return new AddCategoryResponse(dto.id());
        }
    }
}
