package com.pi.kursy.courses.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddCourseFacade {

    private final AddCourseRepository repository;
    private final AddCourseFactory factory;

    AddCourseResponseDto addCourse(AddCourseDto dto) throws AddCourseFactory.CreateEntityError, AddCourseEntity.CourseValidationError {
        var entity = factory.create(dto);
        var snapshot = entity.save();
        repository.save(snapshot);
        return new AddCourseResponseDto(snapshot.id());
    }
}
