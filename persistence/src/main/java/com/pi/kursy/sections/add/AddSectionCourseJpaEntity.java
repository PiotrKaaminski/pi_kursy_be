package com.pi.kursy.sections.add;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "courses")
class AddSectionCourseJpaEntity {
    @Id
    private String id;

    protected AddSectionCourseJpaEntity() {}

    private AddSectionCourseJpaEntity(String id) {
        this.id = id;
    }

    AddSectionCourseSnapshot toSnapshot(Integer sectionsCount) {
        return new AddSectionCourseSnapshot(id, sectionsCount);
    }

    static AddSectionCourseJpaEntity fromSnapshot(AddSectionCourseSnapshot snapshot) {
        return new AddSectionCourseJpaEntity(snapshot.id());
    }
}
