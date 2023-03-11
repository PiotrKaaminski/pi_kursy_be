package com.pi.kursy.users.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RegisterUserJpaRepository extends JpaRepository<UserRegisterEntity, Long> {
    boolean existsByUsername(String username);
}
