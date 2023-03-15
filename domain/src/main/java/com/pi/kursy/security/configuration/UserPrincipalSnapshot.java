package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.Builder;

@Builder
record UserPrincipalSnapshot(String id, String username, String password, RoleEnum role) {
}
