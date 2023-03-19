package com.pi.kursy.courses.update;

import java.util.Set;

record UpdateCourseDto(
        String id,
        String name,
        Float price,
        Set<String> categoryIds,
        String teacherId
) {
}
