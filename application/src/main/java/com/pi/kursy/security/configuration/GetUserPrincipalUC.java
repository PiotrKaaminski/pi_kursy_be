package com.pi.kursy.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class GetUserPrincipalUC {

    private final UserPrincipalRepository repository;

    Optional<UserPrincipalSnapshot> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
