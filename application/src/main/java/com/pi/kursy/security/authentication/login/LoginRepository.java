package com.pi.kursy.security.authentication.login;

import java.util.Optional;

interface LoginRepository {
    Optional<UserSnapshot> findByUsername(String username);
}
