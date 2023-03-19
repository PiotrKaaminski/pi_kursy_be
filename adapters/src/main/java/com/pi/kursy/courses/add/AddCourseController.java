package com.pi.kursy.courses.add;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
class AddCourseController {

    private final AddCourseFacade facade;

    @PostMapping
    AddCourseResponse addCourse(@RequestBody AddCourseRequest request) throws Exception {
        var responseDto = facade.addCourse(request.toDto());
        return AddCourseResponse.fromDto(responseDto);
    }


    record AddCourseRequest(
            String name,
            Float price,
            Set<String> categoryIds,
            String teacherId
    ) {
        AddCourseDto toDto() {
            return new AddCourseDto(name, price, categoryIds, teacherId);
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
