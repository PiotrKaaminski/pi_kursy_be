package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
class UserPrincipalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    UserPrincipalSnapshot toSnapshot() {
        return new UserPrincipalSnapshot(externalId, username, password, role);
    }
}
