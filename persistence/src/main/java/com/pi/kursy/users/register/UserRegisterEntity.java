package com.pi.kursy.users.register;

import com.pi.kursy.security.shared.RoleEnum;
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
    private ZonedDateTime creationDate;


    @PersistenceCreator
    protected UserRegisterEntity() {}

    UserRegisterEntity(String id, String username, String password, RoleEnum role, ZonedDateTime creationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
    }

    static UserRegisterEntity fromSnapshot(RegisterUserSnapshot snapshot) {
        return new UserRegisterEntity(
                snapshot.id(),
                snapshot.username(),
                snapshot.password(),
                snapshot.role(),
                snapshot.creationDate()
        );
    }
}
