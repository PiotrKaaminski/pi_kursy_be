package com.pi.kursy.users.register;

import java.util.Optional;

interface ChangePasswordRepository {
    Optional<ChangePasswordSnapshot> findById(String id);
    void update(ChangePasswordSnapshot snapshot);
}
