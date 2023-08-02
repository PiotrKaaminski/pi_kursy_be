package com.pi.kursy.users.query.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface GetUsersJpaRepository extends JpaRepository<GetUsersJpaEntity, String>, JpaSpecificationExecutor<GetUsersJpaEntity> {
}
