package com.pi.kursy.courses.query.list;

import java.util.List;

record GetCoursesResponseDto(
        List<GetCoursesEntryDto> rows,
        Long count
) {
}
