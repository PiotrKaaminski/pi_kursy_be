package com.pi.kursy.security.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserPrincipalJpaRepository extends JpaRepository<UserPrincipalEntity, String> {
}
