package com.pi.kursy.categories.add;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
class AddCategoryJpaEntity {
    @Id
    private String id;
    private String name;

    protected AddCategoryJpaEntity() {}

    private AddCategoryJpaEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    static AddCategoryJpaEntity fromSnapshot(AddCategorySnapshot snapshot) {
        return new AddCategoryJpaEntity(
                snapshot.id(),
                snapshot.name()
        );
    }

}
