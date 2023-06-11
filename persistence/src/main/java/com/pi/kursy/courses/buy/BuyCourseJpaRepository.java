package com.pi.kursy.courses.buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuyCourseJpaRepository extends JpaRepository<BuyCourseJpaEntity, String> {
    boolean existsByClient_IdAndCourse_Id(String clientId, String courseId);

}
