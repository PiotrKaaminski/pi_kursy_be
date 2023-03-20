package com.pi.kursy.sections.add;

import java.util.Optional;

interface AddSectionRepository {

    void save(AddSectionSnapshot snapshot);
    Optional<AddSectionCourseSnapshot> findCourseById(String courseId);
}
