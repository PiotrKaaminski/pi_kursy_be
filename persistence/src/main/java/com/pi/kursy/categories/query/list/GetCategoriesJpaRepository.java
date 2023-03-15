package com.pi.kursy.categories.query.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GetCategoriesJpaRepository extends JpaRepository<CategoryJpaEntity, String> {
}
