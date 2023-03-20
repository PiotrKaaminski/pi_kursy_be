package com.pi.kursy.courses.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddCourseFactory {

    private final AddCourseRepository repository;

    AddCourseEntity create(AddCourseDto dto) throws Exception {
        return new AddCourseEntity(
                repository,
                dto.name(),
                dto.price(),
                dto.categoryIds(),
                getTeacher(dto.teacherId()),
                dto.description()
        );
    }

    private AddCourseEntity.Teacher getTeacher(String id) throws Exception {
        return repository.findTeacherById(id)
                .map(AddCourseEntity.Teacher::fromSnapshot)
                .orElseThrow(() -> new Exception("teacher with id " + id + " doesn't exist"));
    }
}
