package com.pi.kursy.sections.add;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "courses")
class AddSectionCourseJpaEntity {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private AddSectionCourseOwnerJpaEntity owner;

    protected AddSectionCourseJpaEntity() {}

    private AddSectionCourseJpaEntity(String id) {
        this.id = id;
    }

    AddSectionCourseSnapshot toSnapshot(Integer sectionsCount) {
        return new AddSectionCourseSnapshot(id, sectionsCount, owner.id);
    }

    static AddSectionCourseJpaEntity fromSnapshot(AddSectionCourseSnapshot snapshot) {
        return new AddSectionCourseJpaEntity(snapshot.id());
    }

    @Entity
    @Table(name = "users")
    static class AddSectionCourseOwnerJpaEntity {
        @Id
        private String id;
    }
}
