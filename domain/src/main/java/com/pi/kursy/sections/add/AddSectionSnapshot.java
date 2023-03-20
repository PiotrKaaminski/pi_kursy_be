package com.pi.kursy.sections.add;

record AddSectionSnapshot(
        String id,
        String title,
        Integer sequence,
        AddSectionCourseSnapshot course
) {
}
