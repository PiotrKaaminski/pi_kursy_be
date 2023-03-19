package com.pi.kursy.courses.add;

import com.pi.kursy.security.shared.RoleEnum;

record AddCourseTeacherSnapshot(
        String id,
        RoleEnum role
) {
}
