package com.pi.kursy.sections.add;

import jakarta.persistence.*;

@Entity
@Table(name = "sections")
class AddSectionJpaEntity {
    @Id
    private String id;
    private String title;
    private Integer sequence;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private AddSectionCourseJpaEntity course;

    protected AddSectionJpaEntity() {
    }

    private AddSectionJpaEntity(String id, String title, Integer sequence, AddSectionCourseJpaEntity course) {
        this.id = id;
        this.title = title;
        this.sequence = sequence;
        this.course = course;
    }

    static AddSectionJpaEntity fromSnapshot(AddSectionSnapshot snapshot) {
        return new AddSectionJpaEntity(
                snapshot.id(),
                snapshot.title(),
                snapshot.sequence(),
                AddSectionCourseJpaEntity.fromSnapshot(snapshot.course())
        );
    }


}
