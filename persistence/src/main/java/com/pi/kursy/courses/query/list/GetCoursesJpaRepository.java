package com.pi.kursy.courses.query.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GetCoursesJpaRepository extends
        JpaRepository<GetCoursesJpaEntity, String>,
        JpaSpecificationExecutor<GetCoursesJpaEntity> {
}
