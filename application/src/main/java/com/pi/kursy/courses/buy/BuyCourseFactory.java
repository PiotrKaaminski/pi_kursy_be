package com.pi.kursy.courses.buy;

import com.pi.kursy.shared.GenericException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BuyCourseFactory {

    private final BuyCourseRepository repository;

    BuyCourseEntity create(BuyCourseDto dto) throws CreateEntityException {
        return new BuyCourseEntity(
                findClient(dto.clientId()),
                findCourse(dto.courseId()),
                repository);
    }

    private BuyCourseEntity.Client findClient(String clientId) throws CreateEntityException {
        return repository.findClientById(clientId)
                .map(BuyCourseEntity.Client::fromSnapshot)
                .orElseThrow(() -> new CreateEntityException("Client with id " + clientId + " doesn't exist", CreateEntityException.Status.CLIENT_NOT_FOUND));
    }

    private BuyCourseEntity.Course findCourse(String courseId) throws CreateEntityException {
        return repository.findCourseById(courseId)
                .map(BuyCourseEntity.Course::fromSnapshot)
                .orElseThrow(() -> new CreateEntityException("Course with id " + courseId + " doesn't exist", CreateEntityException.Status.COURSE_NOT_FOUND));
    }

    class CreateEntityException extends GenericException {

        private final Status status;

        CreateEntityException(String message, Status status) {
            super(message);
            this.status = status;
        }

        public String getStatus() {
            return status.name();
        }

        enum Status {
            CLIENT_NOT_FOUND,
            COURSE_NOT_FOUND
        }
    }

}
