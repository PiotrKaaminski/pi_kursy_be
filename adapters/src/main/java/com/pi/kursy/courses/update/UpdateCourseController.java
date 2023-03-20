package com.pi.kursy.courses.update;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
class UpdateCourseController {

    private final UpdateCourseFacade facade;

    @PatchMapping("/{id}")
    void updateCourse(@RequestBody UpdateCourseRequest request, @PathVariable String id) throws Exception {
        facade.updateCourse(request.toDto(id));
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
