package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.users.shared.UserStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name = "users")
class UserRegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalId;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @PersistenceCreator
    protected UserRegisterEntity() {}

    UserRegisterEntity(String externalId, String username, String password, RoleEnum role, UserStatus status) {
        this.externalId = externalId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    static UserRegisterEntity fromSnapshot(RegisterUserSnapshot snapshot) {
        return new UserRegisterEntity(
                snapshot.externalId(),
                snapshot.username(),
                snapshot.password(),
                snapshot.role(),
                snapshot.status()
        );
    }
}
