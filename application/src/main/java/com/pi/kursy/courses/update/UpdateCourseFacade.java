package com.pi.kursy.courses.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateCourseFacade {

    private final UpdateCourseRepository repository;
    private final UpdateCourseFactory factory;

    void updateCourse(UpdateCourseDto dto) throws Exception {
        var entity = factory.create(dto);
        var snapshot = entity.save();
        repository.save(snapshot);
    }
}
