package com.pi.kursy.categories.query.list;

import java.util.List;

record GetCategoriesResponseDto(
        List<GetCategoriesEntryDto> rows
) {
}


