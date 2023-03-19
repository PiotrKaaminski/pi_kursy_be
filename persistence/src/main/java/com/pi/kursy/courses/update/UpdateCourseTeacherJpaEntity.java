package com.pi.kursy.courses.update;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
class UpdateCourseTeacherJpaEntity {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    protected UpdateCourseTeacherJpaEntity() {
    }

    private UpdateCourseTeacherJpaEntity(String id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    UpdateCourseTeacherSnapshot toSnapshot() {
        return new UpdateCourseTeacherSnapshot(id, role);
    }

    static UpdateCourseTeacherJpaEntity fromSnapshot(UpdateCourseTeacherSnapshot snapshot) {
        return new UpdateCourseTeacherJpaEntity(snapshot.id(), snapshot.role());
    }
}
