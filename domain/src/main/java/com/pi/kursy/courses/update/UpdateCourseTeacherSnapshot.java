package com.pi.kursy.courses.update;

import com.pi.kursy.security.shared.RoleEnum;

record UpdateCourseTeacherSnapshot(
        String id,
        RoleEnum role
) { }
