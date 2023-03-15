package com.pi.kursy.categories.query.list;

import java.util.List;

interface GetCategoriesRepository {
    List<GetCategoriesEntryDto> getAll();
}
