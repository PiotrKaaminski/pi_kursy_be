package com.pi.kursy.courses.add;

import com.pi.kursy.shared.ErrorResponse;
import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
class AddCourseController {

    private final AddCourseFacade facade;

    @PostMapping
    AddCourseResponse addCourse(@RequestBody AddCourseRequest request) throws AddCourseFactory.CreateEntityError, AddCourseEntity.CourseValidationError {
        var responseDto = facade.addCourse(request.toDto());
        return AddCourseResponse.fromDto(responseDto);
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<ErrorResponse> handleError(GenericException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.fromGenericException(error)
        );
    }


    record AddCourseRequest(
            String name,
            Float price,
            Set<String> categoryIds,
            String teacherId,
            String description
    ) {
        AddCourseDto toDto() {
            return new AddCourseDto(name, price, categoryIds, teacherId, description);
        }
    }

    record AddCourseResponse(
            String id
    ) {
        static AddCourseResponse fromDto(AddCourseResponseDto dto) {
            return new AddCourseResponse(dto.id());
        }
    }
}
