package com.pi.kursy.users.register;

record ChangePasswordDto(String userId, String oldPassword, String newPassword) {
}
