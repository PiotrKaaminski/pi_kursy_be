package com.pi.kursy.courses.query.single;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class GetCourseFacade {

    private final GetCourseRepository repository;

    GetCourseResponseDto getCourse(String id, Optional<String> userId) {
        return repository.findById(id)
                .map(snapshot -> mapToResponse(snapshot, userId))
                .orElseThrow(() -> new RuntimeException("Course with id " + id + " doesn't exist"));
    }

    private GetCourseResponseDto mapToResponse(GetCourseSnapshot snapshot, Optional<String> userId) {
        return new GetCourseResponseDto(
                snapshot.id(),
                snapshot.name(),
                snapshot.price(),
                snapshot.description(),
                userId.map(id -> repository.userHasAccess(snapshot.id(), id)).orElse(false),
                map(snapshot.teacher()),
                snapshot.categories().stream().map(this::map).collect(Collectors.toList()),
                snapshot.sections().stream().map(this::map).collect(Collectors.toList())
        );
    }

    private GetCourseResponseDto.Teacher map(GetCourseSnapshot.Teacher teacher) {
        return new GetCourseResponseDto.Teacher(
                teacher.id(),
                teacher.username()
        );
    }

    private GetCourseResponseDto.Category map(GetCourseSnapshot.Category category) {
        return new GetCourseResponseDto.Category(
                category.id(),
                category.name()
        );
    }

    private GetCourseResponseDto.Section map(GetCourseSnapshot.Section section) {
        return new GetCourseResponseDto.Section(
                section.id(),
                section.title(),
                section.sequence()
        );
    }

}
