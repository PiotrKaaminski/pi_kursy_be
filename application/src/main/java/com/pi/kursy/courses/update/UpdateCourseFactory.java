package com.pi.kursy.courses.update;

import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UpdateCourseFactory {

    private final UpdateCourseRepository repository;

    UpdateCourseEntity create(UpdateCourseDto dto) throws CreateEntityException {
        var courseSnapshot = repository.findById(dto.id()).orElseThrow(
                () -> new CreateEntityException("Course with id " + dto.id() + " doesn't exist", CreateEntityException.Status.COURSE_NOT_FOUND)
        );
        return mapToEntity(courseSnapshot, dto);
    }

    private UpdateCourseEntity mapToEntity(UpdateCourseSnapshot snapshot, UpdateCourseDto dto) throws CreateEntityException {
        return new UpdateCourseEntity(
                repository,
                snapshot.id(),
                dto.name() == null ? snapshot.name() : dto.name(),
                dto.price() == null ? snapshot.price() : dto.price(),
                dto.categoryIds() == null ? snapshot.categoryIds() : dto.categoryIds(),
                dto.teacherId() == null ? UpdateCourseEntity.Teacher.fromSnapshot(snapshot.teacher()) : findTeacher(dto.teacherId()),
                dto.description() == null ? snapshot.description() : dto.description()
        );
    }

    private UpdateCourseEntity.Teacher findTeacher(String id) throws CreateEntityException {
        return repository.findTeacherById(id)
                .map(UpdateCourseEntity.Teacher::fromSnapshot)
                .orElseThrow(() -> new CreateEntityException("Teacher with id " + id + " doesn't exist", CreateEntityException.Status.TEACHER_NOT_FOUND));
    }

    static class CreateEntityException extends GenericException {

        private final Status status;

        CreateEntityException(String message, Status status) {
            super(message);
            this.status = status;
        }

        public String getStatus() {
            return status.name();
        }

        enum Status {
            TEACHER_NOT_FOUND,
            COURSE_NOT_FOUND
        }
    }

}
