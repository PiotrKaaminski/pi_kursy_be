package com.pi.kursy.courses.add;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddCourseTeacherJpaRepository extends JpaRepository<AddCourseTeacherJpaEntity, String> {
}
