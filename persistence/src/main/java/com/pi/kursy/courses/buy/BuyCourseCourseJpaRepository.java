package com.pi.kursy.courses.buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuyCourseCourseJpaRepository extends JpaRepository<BuyCourseJpaEntity.Course, String> {

}