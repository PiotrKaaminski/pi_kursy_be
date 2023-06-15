package com.pi.kursy.categories.add;

import com.pi.kursy.shared.GenericException;
import lombok.Getter;
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

    AddCategorySnapshot save() throws CategoryValidationError {
        validateName();
        fillMissingValues();

        return toSnapshot();
    }

    private void validateName() throws CategoryValidationError {
        if (!StringUtils.hasText(name)) {
            throw new CategoryValidationError("category name cannot be empty", CategoryValidationError.Status.NAME_EMPTY);
        }
        if (name.length() > 50) {
            throw new CategoryValidationError("category name cannot be longer than 50 characters", CategoryValidationError.Status.NAME_TOO_LONG);
        }
        if (repository.existsByName(name)) {
            throw new CategoryValidationError("category with the same name already exists", CategoryValidationError.Status.NAME_NOT_UNIQUE);
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

    static class CategoryValidationError extends GenericException {

        private final Status status;

        CategoryValidationError(String message, Status status) {
            super(message);
            this.status = status;
        }

        @Override
        public String getStatus() {
            return status.name();
        }

        enum Status {
            NAME_EMPTY,
            NAME_TOO_LONG,
            NAME_NOT_UNIQUE
        }
    }
}
