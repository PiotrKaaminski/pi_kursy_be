package com.pi.kursy.sections.add;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddSectionCourseJpaRepository extends JpaRepository<AddSectionCourseJpaEntity, String> {
}
