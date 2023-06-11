package com.pi.kursy.courses.query.single;

import java.util.List;

record GetCourseResponseDto(
        String id,
        String name,
        Float price,
        String description,
        Teacher teacher,
        List<Category> categories,
        List<Section> sections
) {

    record Teacher(
            String id,
            String username
    ) {}

    record Category(
            String id,
            String name
    ) {}

    record Section(
        String id,
        String title,
        Integer sequence
    ) {}
}
