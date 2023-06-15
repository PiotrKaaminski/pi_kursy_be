package com.pi.kursy.categories.add;

import com.pi.kursy.shared.ErrorResponse;
import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
class AddCategoryController {

    private final AddCategoryFacade facade;

    @PostMapping
    AddCategoryResponse addCategory(@RequestBody AddCategoryRequest request) throws AddCategoryEntity.CategoryValidationError {
        var responseDto = facade.addCategory(request.toDto());
        return AddCategoryResponse.fromDto(responseDto);
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<ErrorResponse> handleError(GenericException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.fromGenericException(error)
        );
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
