package com.pi.kursy.courses.update;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
class UpdateCourseCategoryJpaEntity {
    @Id
    private String id;

    protected UpdateCourseCategoryJpaEntity() {}

    private UpdateCourseCategoryJpaEntity(String id) {
        this.id = id;
    }

    static UpdateCourseCategoryJpaEntity fromSnapshot(String id) {
        return new UpdateCourseCategoryJpaEntity(id);
    }

    String getId() {
        return id;
    }
}