package com.pi.kursy.courses.add;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.shared.GenericException;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.UUID;

class AddCourseEntity {

    private final AddCourseRepository repository;

    private String id;
    private String name;
    private Float price;
    private Set<String> categoryIds;
    private Teacher teacher;
    private String description;

    AddCourseEntity(AddCourseRepository repository, String name, Float price, Set<String> categoryIds, Teacher teacher, String description) {
        this.repository = repository;
        this.name = name;
        this.price = price;
        this.categoryIds = categoryIds;
        this.teacher = teacher;
        this.description = description;
    }

    AddCourseSnapshot save() throws CourseValidationError {
        validateName();
        validatePrice();
        validateCategoryIds();
        validateDescription();

        fillMissingFields();

        return toSnapshot();
    }

    private void validateName() throws CourseValidationError {
        if (!StringUtils.hasText(name)) {
            throw new CourseValidationError("Name cannot be empty", CourseValidationError.Status.NAME_EMPTY);
        }
        if (name.length() < 10) {
            throw new CourseValidationError("Name cannot be shorter than 10 characters", CourseValidationError.Status.NAME_TOO_SHORT);
        }
        if (name.length() > 255) {
            throw new CourseValidationError("Name cannot be longer than 255 characters", CourseValidationError.Status.NAME_TOO_LONG);
        }
    }
    private void validatePrice() throws CourseValidationError {
        if (price == null) {
            throw new CourseValidationError("Price cannot be null", CourseValidationError.Status.PRICE_EMPTY);
        }
        if (price <= 0) {
            throw new CourseValidationError("Price cannot be less than or equal to 0", CourseValidationError.Status.PRICE_TOO_LOW);
        }
        if (price >= 100_000) {
            throw new CourseValidationError("Price cannot be grater than 100 000", CourseValidationError.Status.PRICE_TOO_HIGH);
        }
    }
    private void validateCategoryIds() throws CourseValidationError {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new CourseValidationError("Categories cannot be empty", CourseValidationError.Status.CATEGORIES_EMPTY);
        }
        for (var categoryId : categoryIds) {
            if (repository.categoryDoesntExistById(categoryId)) {
                throw new CourseValidationError("Category with id " + categoryId + " doesn't exist", CourseValidationError.Status.CATEGORY_NOT_FOUND);
            }
        }
    }

    private void validateDescription() throws CourseValidationError {
        if (!StringUtils.hasText(description)) {
            throw new CourseValidationError("Description cannot be empty", CourseValidationError.Status.DESCRIPTION_EMPTY);
        }
        if (description.length() < 10) {
            throw new CourseValidationError("Description cannot be shorter than 10 characters", CourseValidationError.Status.DESCRIPTION_TOO_SHORT);
        }
    }

    private void fillMissingFields() {
        this.id = UUID.randomUUID().toString();
    }

    private AddCourseSnapshot toSnapshot() {
        return new AddCourseSnapshot(
                id,
                name,
                price,
                categoryIds,
                teacher.toSnapshot(),
                description
        );
    }

    static class Teacher {
        private String id;
        private RoleEnum role;

        Teacher(String id, RoleEnum role) {
            this.id = id;
            this.role = role;
        }

        static Teacher fromSnapshot(AddCourseTeacherSnapshot snapshot) {
            return new Teacher(snapshot.id(), snapshot.role());
        }

        private AddCourseTeacherSnapshot toSnapshot() {
            return new AddCourseTeacherSnapshot(
                    id,
                    role
            );
        }
    }

    static class CourseValidationError extends GenericException {

        private final Status status;

        CourseValidationError(String message, Status status) {
            super(message);
            this.status = status;
        }

        public String getStatus() {
            return status.name();
        }

        enum Status {
            NAME_EMPTY, NAME_TOO_SHORT, NAME_TOO_LONG,
            PRICE_EMPTY, PRICE_TOO_LOW, PRICE_TOO_HIGH,
            CATEGORIES_EMPTY, CATEGORY_NOT_FOUND,
            DESCRIPTION_EMPTY, DESCRIPTION_TOO_SHORT

        }

    }
}
