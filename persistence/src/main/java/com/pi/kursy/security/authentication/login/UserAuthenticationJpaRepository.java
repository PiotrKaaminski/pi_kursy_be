package com.pi.kursy.security.authentication.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserAuthenticationJpaRepository extends JpaRepository<LoginUserEntity, String> {
}
