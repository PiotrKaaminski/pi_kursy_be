package com.pi.kursy.courses.query.list;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetCoursesFacade {

    private final GetCoursesRepository repository;

    GetCoursesResponseDto getCourses() {
        return new GetCoursesResponseDto(repository.getAll());
    }
}
