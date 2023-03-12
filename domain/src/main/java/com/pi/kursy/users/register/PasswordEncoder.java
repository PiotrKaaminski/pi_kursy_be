package com.pi.kursy.users.register;

interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String encodedPassword);
}
