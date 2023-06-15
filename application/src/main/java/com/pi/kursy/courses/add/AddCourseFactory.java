package com.pi.kursy.courses.add;

import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AddCourseFactory {

    private final AddCourseRepository repository;

    AddCourseEntity create(AddCourseDto dto) throws CreateEntityError {
        return new AddCourseEntity(
                repository,
                dto.name(),
                dto.price(),
                dto.categoryIds(),
                getTeacher(dto.teacherId()),
                dto.description()
        );
    }

    private AddCourseEntity.Teacher getTeacher(String id) throws CreateEntityError {
        return repository.findTeacherById(id)
                .map(AddCourseEntity.Teacher::fromSnapshot)
                .orElseThrow(() -> new CreateEntityError("teacher with id " + id + " doesn't exist"));
    }

    static class CreateEntityError extends GenericException {
        CreateEntityError(String message) {
            super(message);
        }

        public String getStatus() {
            return "TEACHER_NOT_FOUND";
        }
    }
}
