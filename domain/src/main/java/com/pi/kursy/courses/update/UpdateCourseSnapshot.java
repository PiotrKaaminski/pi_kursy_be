package com.pi.kursy.courses.update;

import java.util.Set;

record UpdateCourseSnapshot(
        String id,
        String name,
        Float price,
        Set<String> categoryIds,
        UpdateCourseTeacherSnapshot teacher,
        String description
) {
}
