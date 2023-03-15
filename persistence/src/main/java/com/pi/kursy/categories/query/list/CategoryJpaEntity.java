package com.pi.kursy.categories.query.list;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
class CategoryJpaEntity {
    @Id
    private String id;
    private String name;

    GetCategoriesEntryDto toDto() {
        return new GetCategoriesEntryDto(id, name);
    }
}
