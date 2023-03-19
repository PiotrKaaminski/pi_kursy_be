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

    AddCourseTeacherSnapshot toSnapshot() {
        return new AddCourseTeacherSnapshot(id, role);
    }
}
