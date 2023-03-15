package com.pi.kursy.categories.add;

import org.springframework.util.StringUtils;

import java.util.UUID;

class AddCategoryEntity {

    private final AddCategoryRepository repository;

    private String id;
    private final String name;

    private AddCategoryEntity(AddCategoryRepository repository, String name) {
        this.repository = repository;
        this.name = name;
    }

    AddCategorySnapshot save() throws Exception {
        validateName();
        fillMissingValues();

        return toSnapshot();
    }

    private void validateName() throws Exception {
        if (!StringUtils.hasText(name)) {
            throw new Exception("category name cannot be empty");
        }
        if (name.length() > 50) {
            throw new Exception("category name cannot be longer than 50 characters");
        }
        if (repository.existsByName(name)) {
            throw new Exception("category with the same name already exists");
        }
    }
    private void fillMissingValues() {
        this.id = UUID.randomUUID().toString();
    }

    private AddCategorySnapshot toSnapshot() {
        return new AddCategorySnapshot(id, name);
    }

    static class Builder {
        private AddCategoryRepository repository;
        private String name;

        Builder repository(AddCategoryRepository repository) {
            this.repository = repository;
            return this;
        }

        Builder name(String name) {
            this.name = name;
            return this;
        }

        AddCategoryEntity build() {
            return new AddCategoryEntity(repository, name);
        }
    }
}
