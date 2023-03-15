package com.pi.kursy.categories.add;

interface AddCategoryRepository {
    void save(AddCategorySnapshot snapshot);
    boolean existsByName(String name);
}
