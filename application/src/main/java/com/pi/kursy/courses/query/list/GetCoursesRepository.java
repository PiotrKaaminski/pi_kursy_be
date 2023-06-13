package com.pi.kursy.courses.query.list;

import java.util.List;

interface GetCoursesRepository {
    GetCoursesResponseDto getAll(GetCoursesFilters filters);
}
