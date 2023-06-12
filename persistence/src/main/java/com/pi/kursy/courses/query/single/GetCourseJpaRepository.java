package com.pi.kursy.courses.query.single;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GetCourseJpaRepository extends JpaRepository<GetCourseJpaEntity, String> {
    boolean existsByIdAndUsersWithAccess_Id(String courseId, String userId);
}
