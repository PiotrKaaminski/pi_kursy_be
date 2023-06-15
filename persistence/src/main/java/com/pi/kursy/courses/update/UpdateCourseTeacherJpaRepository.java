package com.pi.kursy.courses.update;

import com.pi.kursy.security.shared.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UpdateCourseTeacherJpaRepository extends JpaRepository<UpdateCourseTeacherJpaEntity, String> {

    Optional<UpdateCourseTeacherJpaEntity> findByIdAndRole(String id, RoleEnum role);
}
