package com.pi.kursy.courses.query.list;

import java.util.List;

interface GetCoursesRepository {
    List<GetCoursesEntryDto> getAll(GetCoursesFilters filters);
}
