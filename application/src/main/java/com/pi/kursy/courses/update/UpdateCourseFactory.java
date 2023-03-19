package com.pi.kursy.courses.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateCourseFactory {

    private final UpdateCourseRepository repository;

    UpdateCourseEntity create(UpdateCourseDto dto) throws Exception {
        var courseSnapshot = repository.findById(dto.id()).orElseThrow(
                () -> new Exception("Course with id " + dto.id() + " doesn't exist")
        );
        return mapToEntity(courseSnapshot, dto);
    }

    private UpdateCourseEntity mapToEntity(UpdateCourseSnapshot snapshot, UpdateCourseDto dto) throws Exception {
        return new UpdateCourseEntity(
                repository,
                snapshot.id(),
                dto.name() == null ? snapshot.name() : dto.name(),
                dto.price() == null ? snapshot.price() : dto.price(),
                dto.categoryIds() == null ? snapshot.categoryIds() : dto.categoryIds(),
                dto.teacherId() == null ? UpdateCourseEntity.Teacher.fromSnapshot(snapshot.teacher()) : findTeacher(dto.teacherId())
        );
    }

    private UpdateCourseEntity.Teacher findTeacher(String id) throws Exception {
        return repository.findTeacherById(id)
                .map(UpdateCourseEntity.Teacher::fromSnapshot)
                .orElseThrow(() -> new Exception("Teacher with id " + id + " doesn't exist"));
    }

}
