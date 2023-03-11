package com.pi.kursy.users.register;

import java.util.Optional;

interface ChangePasswordRepository {
    Optional<ChangePasswordSnapshot> findByExternalId(String externalId);
    void update(ChangePasswordSnapshot snapshot);
}
