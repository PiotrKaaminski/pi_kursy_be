package com.pi.kursy.courses.buy;

import java.util.Optional;

interface BuyCourseRepository {
    Optional<BuyCourseSnapshot.Course> findCourseById(String id);
    Optional<BuyCourseSnapshot.Client> findClientById(String id);

    boolean accessExists(String clientId, String courseId);

    void save(BuyCourseSnapshot snapshot);
}
