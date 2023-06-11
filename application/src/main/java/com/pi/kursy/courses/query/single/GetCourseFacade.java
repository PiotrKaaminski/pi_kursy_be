package com.pi.kursy.courses.query.single;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class GetCourseFacade {

    private final GetCourseRepository repository;

    GetCourseResponseDto getCourse(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course with id " + id + " doesn't exist"));
    }
}
