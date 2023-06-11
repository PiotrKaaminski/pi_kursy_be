package com.pi.kursy.courses.buy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BuyCourseFactory {

    private final BuyCourseRepository repository;

    BuyCourseEntity create(BuyCourseDto dto) throws Exception {
        return new BuyCourseEntity(
                findClient(dto.clientId()),
                findCourse(dto.courseId()),
                repository);
    }

    private BuyCourseEntity.Client findClient(String clientId) throws Exception {
        return repository.findClientById(clientId)
                .map(BuyCourseEntity.Client::fromSnapshot)
                .orElseThrow(() -> new Exception("Client with id " + clientId + " doesn't exist"));
    }

    private BuyCourseEntity.Course findCourse(String courseId) throws Exception {
        return repository.findCourseById(courseId)
                .map(BuyCourseEntity.Course::fromSnapshot)
                .orElseThrow(() -> new Exception("Course with id " + courseId + " doesn't exist"));
    }

}
