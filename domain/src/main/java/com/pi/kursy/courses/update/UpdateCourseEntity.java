package com.pi.kursy.courses.update;

import com.pi.kursy.security.shared.RoleEnum;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.UUID;

class UpdateCourseEntity {

    private final UpdateCourseRepository repository;

    private String id;
    private String name;
    private Float price;
    private Set<String> categoryIds;
    private Teacher teacher;

    UpdateCourseEntity(UpdateCourseRepository repository, String id, String name, Float price, Set<String> categoryIds, Teacher teacher) {
        this.repository = repository;
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryIds = categoryIds;
        this.teacher = teacher;
    }

    UpdateCourseSnapshot save() throws Exception {
        validateName();
        validatePrice();
        validateCategoryIds();
        validateTeacher();

        return toSnapshot();
    }

    private void validateName() throws Exception {
        if (!StringUtils.hasText(name)) {
            throw new Exception("Name cannot be empty");
        }
        if (name.length() < 10) {
            throw new Exception("Name cannot be shorter than 10 characters");
        }
        if (name.length() > 255) {
            throw new Exception("Name cannot be longer than 255 characters");
        }
    }
    private void validatePrice() throws Exception {
        if (price == null) {
            throw new Exception("Price cannot be null");
        }
        if (price <= 0) {
            throw new Exception("Price cannot be less than or equal to 0");
        }
        if (price >= 100_000) {
            throw new Exception("Price cannot be grater than 100 000");
        }
    }
    private void validateCategoryIds() throws Exception {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new Exception("Categories cannot be empty");
        }
        for (var categoryId : categoryIds) {
            if (repository.categoryDoesntExistById(categoryId)) {
                throw new Exception("Category with id " + categoryId + " doesn't exist");
            }
        }
    }
    private void validateTeacher() throws Exception {
        teacher.validate();
    }


    private UpdateCourseSnapshot toSnapshot() {
        return new UpdateCourseSnapshot(
                id,
                name,
                price,
                categoryIds,
                teacher.toSnapshot()
        );
    }

    static class Teacher {
        private String id;
        private RoleEnum role;

        Teacher(String id, RoleEnum role) {
            this.id = id;
            this.role = role;
        }

        private void validate() throws Exception {
            if (isNotTeacher()) {
                throw new Exception("Teacher with id " + id + " is not teacher");
            }
        }

        private boolean isNotTeacher() {
            return !role.equals(RoleEnum.TEACHER);
        }

        static Teacher fromSnapshot(UpdateCourseTeacherSnapshot snapshot) {
            return new Teacher(snapshot.id(), snapshot.role());
        }

        private UpdateCourseTeacherSnapshot toSnapshot() {
            return new UpdateCourseTeacherSnapshot(
                    id,
                    role
            );
        }
    }
}
