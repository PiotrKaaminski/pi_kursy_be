package com.pi.kursy.security.configuration;

import java.util.Optional;

interface UserPrincipalRepository {
    Optional<UserPrincipalSnapshot> findByUsername(String username);
}
