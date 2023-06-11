package com.pi.kursy.courses.query.single;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
class GetCourseController {

    private final GetCourseFacade facade;

    @GetMapping("/{id}")
    GetCourseResponse getCourse(@PathVariable String id) {
        var responseDto = facade.getCourse(id);
        return GetCourseResponse.fromDto(responseDto);
    }


    record GetCourseResponse (
            String id,
            String name,
            Float price,
            String description,
            Teacher teacher,
            List<Category> categories,
            List<Section> sections
    ) {

        static GetCourseResponse fromDto(GetCourseResponseDto dto) {
            return new GetCourseResponse(
                    dto.id(),
                    dto.name(),
                    dto.price(),
                    dto.description(),
                    Teacher.fromDto(dto.teacher()),
                    dto.categories().stream().map(Category::fromDto).collect(Collectors.toList()),
                    dto.sections().stream().map(Section::fromDto).collect(Collectors.toList())
            );
        }

        record Teacher (
                String id,
                String username
        ) {
            private static Teacher fromDto(GetCourseResponseDto.Teacher dto) {
                return new Teacher(dto.id(), dto.username());
            }
        }

        record Category (
                String id,
                String name
        ) {
            private static Category fromDto(GetCourseResponseDto.Category dto) {
                return new Category(dto.id(), dto.name());
            }
        }

        record Section (
                String id,
                String title,
                Integer sequence
        ) {
            private static Section fromDto(GetCourseResponseDto.Section dto) {
                return new Section(dto.id(), dto.title(), dto.sequence());
            }
        }
    }

}