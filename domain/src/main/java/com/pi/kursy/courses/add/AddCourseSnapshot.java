package com.pi.kursy.courses.add;

import java.util.Set;

record AddCourseSnapshot(
        String id,
        String name,
        Float price,
        Set<String> categoryIds,
        AddCourseTeacherSnapshot teacher,
        String description
) {
}
