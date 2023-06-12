package com.pi.kursy.courses.query.single;

import java.util.Optional;

interface GetCourseRepository {
    Optional<GetCourseSnapshot> findById(String id);
    boolean userHasAccess(String courseId, String userId);
}
