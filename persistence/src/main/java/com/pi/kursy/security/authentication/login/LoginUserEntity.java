package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
class LoginUserEntity {
    private String externalId;
    @Id
    private String username;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    UserSnapshot toSnapshot() {
        return new UserSnapshot(externalId, username, role);
    }
}
