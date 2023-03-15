package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.users.shared.UserStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
class UserRegisterEntity {
    @Id
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private ZonedDateTime creationDate;


    @PersistenceCreator
    protected UserRegisterEntity() {}

    UserRegisterEntity(String id, String username, String password, RoleEnum role, UserStatus status, ZonedDateTime creationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.creationDate = creationDate;
    }

    static UserRegisterEntity fromSnapshot(RegisterUserSnapshot snapshot) {
        return new UserRegisterEntity(
                snapshot.id(),
                snapshot.username(),
                snapshot.password(),
                snapshot.role(),
                snapshot.status(),
                snapshot.creationDate()
        );
    }
}
