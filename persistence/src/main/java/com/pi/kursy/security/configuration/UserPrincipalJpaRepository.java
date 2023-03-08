package com.pi.kursy.security.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserPrincipalJpaRepository extends JpaRepository<UserPrincipalEntity, Long> {
    Optional<UserPrincipalEntity> findByUsername(String username);
}
