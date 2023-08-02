package com.pi.kursy.users.query.list;

import com.pi.kursy.security.shared.RoleEnum;

record GetUsersEntryDto(
        String id,
        String username,
        RoleEnum role
) {
}
