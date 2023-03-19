package com.pi.kursy.courses.add;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
class AddCourseTeacherJpaEntity {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    protected AddCourseTeacherJpaEntity() {
    }

    private AddCourseTeacherJpaEntity(String id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    AddCourseTeacherSnapshot toSnapshot() {
        return new AddCourseTeacherSnapshot(id, role);
    }

    static AddCourseTeacherJpaEntity fromSnapshot(AddCourseTeacherSnapshot snapshot) {
        return new AddCourseTeacherJpaEntity(snapshot.id(), snapshot.role());
    }
}
