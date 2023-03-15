package com.pi.kursy.security.authentication.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserAuthenticationJpaRepository extends JpaRepository<LoginUserEntity, String> {
    Optional<LoginUserEntity> findByUsername(String username);
}
