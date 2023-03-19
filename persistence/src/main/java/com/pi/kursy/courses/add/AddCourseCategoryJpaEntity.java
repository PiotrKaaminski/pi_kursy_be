package com.pi.kursy.courses.add;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
class AddCourseCategoryJpaEntity {
    @Id
    private String id;

    protected AddCourseCategoryJpaEntity() {}

    private AddCourseCategoryJpaEntity(String id) {
        this.id = id;
    }

    static AddCourseCategoryJpaEntity fromSnapshot(String id) {
        return new AddCourseCategoryJpaEntity(id);
    }
}