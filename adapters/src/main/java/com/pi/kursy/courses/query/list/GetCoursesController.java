package com.pi.kursy.courses.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("courses")
@RequiredArgsConstructor
class GetCoursesController {

    private final GetCoursesFacade facade;

    @GetMapping
    GetCoursesResponse getCourses(GetCoursesFilters filters) {
        var responseDto = facade.getCourses(filters);
        return GetCoursesResponse.fromDto(responseDto);
    }

    record GetCoursesResponse(
            List<GetCoursesEntry> rows
    ) {
        static GetCoursesResponse fromDto(GetCoursesResponseDto dto) {
            var entries = dto.rows().stream().map(GetCoursesEntry::fromDto).toList();
            return new GetCoursesResponse(entries);
        }
    }

    record GetCoursesEntry(
        String id,
        String name,
        Float price,
        Teacher teacher,
        List<Category> categories
    ) {

        static GetCoursesEntry fromDto(GetCoursesEntryDto dto) {
            return new GetCoursesEntry(
                    dto.id(),
                    dto.name(),
                    dto.price(),
                    Teacher.fromDto(dto.teacher()),
                    dto.categories().stream().map(Category::fromDto).collect(Collectors.toList())
            );
        }

        record Teacher(
            String id,
            String username
        ) {
            static Teacher fromDto(GetCoursesEntryDto.Teacher dto) {
                return new Teacher(dto.id(), dto.username());
            }
        }

        record Category(
                String id,
                String name
        ) {
            static Category fromDto(GetCoursesEntryDto.Category dto) {
                return new Category(dto.id(), dto.name());
            }
        }
    }
}
