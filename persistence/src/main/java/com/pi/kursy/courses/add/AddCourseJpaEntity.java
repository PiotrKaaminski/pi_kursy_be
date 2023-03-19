package com.pi.kursy.courses.add;

import jakarta.persistence.*;
import org.apache.commons.collections4.iterators.IteratorChain;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "courses")
class AddCourseJpaEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    @ManyToMany
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<AddCourseCategoryJpaEntity> categoryIds;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private AddCourseTeacherJpaEntity teacher;

    protected AddCourseJpaEntity() {}

    private AddCourseJpaEntity(String id, String name, Float price, Set<AddCourseCategoryJpaEntity> categoryIds, AddCourseTeacherJpaEntity teacher) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryIds = categoryIds;
        this.teacher = teacher;
    }

    static AddCourseJpaEntity fromSnapshot(AddCourseSnapshot snapshot) {
        return new AddCourseJpaEntity(
                snapshot.id(),
                snapshot.name(),
                snapshot.price(),
                snapshot.categoryIds().stream().map(AddCourseCategoryJpaEntity::fromSnapshot).collect(Collectors.toSet()),
                AddCourseTeacherJpaEntity.fromSnapshot(snapshot.teacher())
        );
    }
}
