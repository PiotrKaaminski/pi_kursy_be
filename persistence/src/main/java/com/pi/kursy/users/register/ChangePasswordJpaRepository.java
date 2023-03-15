package com.pi.kursy.users.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ChangePasswordJpaRepository extends JpaRepository<ChangePasswordOrmEntity, String> {
}
