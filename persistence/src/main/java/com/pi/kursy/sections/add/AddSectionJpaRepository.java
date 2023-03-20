package com.pi.kursy.sections.add;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddSectionJpaRepository extends JpaRepository<AddSectionJpaEntity, String> {
    Integer countByCourse_Id(String courseId);
}
