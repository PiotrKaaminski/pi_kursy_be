package com.pi.kursy.sections.add;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class AddSectionFactory {

    private final AddSectionRepository repository;

    AddSectionEntity create(AddSectionDto dto) throws Exception {
        return new AddSectionEntity(
                dto.title(),
                findCourse(dto.courseId()),
                dto.creatorId()
        );
    }

    private AddSectionEntity.Course findCourse(String courseId) throws Exception {
        return repository.findCourseById(courseId)
                .map(AddSectionEntity.Course::fromSnapshot)
                .orElseThrow(() -> new Exception("Course with id " + courseId + " doesn't exist"));
    }

}
