package com.pi.kursy.courses.add;

import java.util.Optional;

interface AddCourseRepository {
    Optional<AddCourseTeacherSnapshot> findTeacherById(String id);
    boolean categoryDoesntExistById(String id);
    void save(AddCourseSnapshot snapshot);
}
