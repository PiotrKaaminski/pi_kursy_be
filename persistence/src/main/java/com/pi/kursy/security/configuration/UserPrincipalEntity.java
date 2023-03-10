package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
class UserPrincipalEntity {
    private String externalId;
    @Id
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    UserPrincipalSnapshot toSnapshot() {
        return new UserPrincipalSnapshot(externalId, username, password, role);
    }
}
