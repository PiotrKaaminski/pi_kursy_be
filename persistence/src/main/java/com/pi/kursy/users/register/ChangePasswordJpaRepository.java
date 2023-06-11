package com.pi.kursy.users.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ChangePasswordJpaRepository extends JpaRepository<ChangePasswordJpaEntity, String> {
}
