package com.pi.kursy.courses.query.single;

import java.util.Optional;

interface GetCourseRepository {
    Optional<GetCourseResponseDto> findById(String id);
}
