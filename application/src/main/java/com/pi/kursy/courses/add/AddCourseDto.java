package com.pi.kursy.courses.add;

import java.util.Set;

record AddCourseDto(
        String name,
        Float price,
        Set<String> categoryIds,
        String teacherId
) {
}
