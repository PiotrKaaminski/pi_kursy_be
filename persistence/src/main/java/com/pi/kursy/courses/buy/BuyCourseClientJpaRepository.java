package com.pi.kursy.courses.buy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuyCourseClientJpaRepository extends JpaRepository<BuyCourseJpaEntity.Client, String> {

}