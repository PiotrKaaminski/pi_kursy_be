package com.pi.kursy.categories.add;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddCategoryJpaRepository extends JpaRepository<AddCategoryJpaEntity, String> {
    boolean existsByName(String name);
}
