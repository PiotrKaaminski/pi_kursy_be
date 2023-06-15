package com.pi.kursy.courses.add;

import com.pi.kursy.security.shared.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AddCourseTeacherJpaRepository extends JpaRepository<AddCourseTeacherJpaEntity, String> {

    Optional<AddCourseTeacherJpaEntity> findByIdAndRole(String id, RoleEnum role);
}
