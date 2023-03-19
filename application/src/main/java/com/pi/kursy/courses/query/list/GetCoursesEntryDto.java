package com.pi.kursy.courses.query.list;

import java.util.List;

record GetCoursesEntryDto(
        String id,
        String name,
        Float price,
        Teacher teacher,
        List<Category> categories
) {

    record Teacher(
            String id,
            String username
    ) {}

    record Category(
            String id,
            String name
    ) {}
}
