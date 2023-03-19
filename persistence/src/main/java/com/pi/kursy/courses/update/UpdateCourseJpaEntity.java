package com.pi.kursy.courses.update;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "courses")
class UpdateCourseJpaEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<UpdateCourseCategoryJpaEntity> categoryIds;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private UpdateCourseTeacherJpaEntity teacher;

    protected UpdateCourseJpaEntity() {}

    private UpdateCourseJpaEntity(String id, String name, Float price, Set<UpdateCourseCategoryJpaEntity> categoryIds, UpdateCourseTeacherJpaEntity teacher) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryIds = categoryIds;
        this.teacher = teacher;
    }

    static UpdateCourseJpaEntity fromSnapshot(UpdateCourseSnapshot snapshot) {
        return new UpdateCourseJpaEntity(
                snapshot.id(),
                snapshot.name(),
                snapshot.price(),
                snapshot.categoryIds().stream().map(UpdateCourseCategoryJpaEntity::fromSnapshot).collect(Collectors.toSet()),
                UpdateCourseTeacherJpaEntity.fromSnapshot(snapshot.teacher())
        );
    }

    UpdateCourseSnapshot toSnapshot() {
        return new UpdateCourseSnapshot(
                id,
                name,
                price,
                categoryIds.stream().map(UpdateCourseCategoryJpaEntity::getId).collect(Collectors.toSet()),
                teacher.toSnapshot()
        );
    }
}
