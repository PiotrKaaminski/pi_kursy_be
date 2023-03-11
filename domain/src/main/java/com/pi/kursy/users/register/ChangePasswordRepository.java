package com.pi.kursy.users.register;

import java.util.Optional;

interface ChangePasswordRepository {
    Optional<ChangePasswordEntity> findByExternalId(String externalId);
}
