package com.pi.kursy.courses.update;

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
class UpdateCourseController {

    private final UpdateCourseFacade facade;

    @PatchMapping("/{id}")
    void updateCourse(@RequestBody UpdateCourseRequest request, @PathVariable String id) throws UpdateCourseFactory.CreateEntityException, UpdateCourseEntity.CourseValidationError {
        facade.updateCourse(request.toDto(id));
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<ErrorResponse> handleError(GenericException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.fromGenericException(error)
        );
    }

    record UpdateCourseRequest(
            String name,
            Float price,
            Set<String> categoryIds,
            String teacherId,
            String description
    ) {
        UpdateCourseDto toDto(String id) {
            return new UpdateCourseDto(id, name, price, categoryIds, teacherId, description);
        }
    }

}
