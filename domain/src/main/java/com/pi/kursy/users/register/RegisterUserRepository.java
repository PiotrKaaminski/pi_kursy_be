package com.pi.kursy.users.register;

interface RegisterUserRepository {
    void save(RegisterUserSnapshot snapshot);
    boolean existsByUsername(String username);
}
