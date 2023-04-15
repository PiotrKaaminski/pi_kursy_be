package com.pi.kursy.sections.add;

import org.springframework.util.StringUtils;

import java.util.UUID;

class AddSectionEntity {

    private String id;
    private final String title;
    private Integer sequence;

    private final Course course;
    private final String creatorId;

    AddSectionEntity(String title, Course course, String creatorId) {
        this.title = title;
        this.course = course;
        this.creatorId = creatorId;
    }

    AddSectionSnapshot save() throws Exception {
        // todo validate if creator is owner of course
        validateName();

        fillMissingFields();

        return toSnapshot();
    }

    private void validateName() throws Exception {
        if (!StringUtils.hasText(this.title)) {
            throw new Exception("Name cannot be empty");
        }
        if (this.title.length() < 5) {
            throw new Exception("Name cannot be shorter than 5 characters");
        }
        if (this.title.length() > 255) {
            throw new Exception("Name cannot be longer than 255 characters");
        }
    }

    private void fillMissingFields() {
        this.id = UUID.randomUUID().toString();
        this.sequence = this.course.sectionsCount;
    }

    private AddSectionSnapshot toSnapshot() {
        return new AddSectionSnapshot(
                id,
                title,
                sequence,
                course.toSnapshot()
        );
    }

    static class Course {
        private final String id;
        private final Integer sectionsCount;
        private final String ownerId;

        private Course(String id, Integer sectionsCount, String ownerId) {
            this.id = id;
            this.sectionsCount = sectionsCount;
            this.ownerId = ownerId;
        }

        static Course fromSnapshot(AddSectionCourseSnapshot snapshot) {
            return new Course(snapshot.id(), snapshot.sectionsCount(), snapshot.ownerId());
        }

        AddSectionCourseSnapshot toSnapshot() {
            return new AddSectionCourseSnapshot(id, sectionsCount, ownerId);
        }

    }

}
