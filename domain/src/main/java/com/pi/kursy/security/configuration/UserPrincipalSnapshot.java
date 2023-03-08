package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.Builder;

@Builder
record UserPrincipalSnapshot(String externalId, String username, String password, RoleEnum role) {
}
