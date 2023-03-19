package com.pi.kursy.courses.update;

import java.util.Optional;

interface UpdateCourseRepository {
    Optional<UpdateCourseSnapshot> findById(String id);
    Optional<UpdateCourseTeacherSnapshot> findTeacherById(String id);
    boolean categoryDoesntExistById(String id);
    void save(UpdateCourseSnapshot snapshot);
}
