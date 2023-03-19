package com.pi.kursy.courses.update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UpdateCourseJpaRepository extends JpaRepository<UpdateCourseJpaEntity, String> {

}
