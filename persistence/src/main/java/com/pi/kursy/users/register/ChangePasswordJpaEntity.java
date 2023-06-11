package com.pi.kursy.users.register;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name = "users")
class ChangePasswordJpaEntity {
    @Id
    private String id;
    private String password;

    @PersistenceCreator
    protected ChangePasswordJpaEntity() {}

    private ChangePasswordJpaEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    ChangePasswordSnapshot toSnapshot() {
        return new ChangePasswordSnapshot(
                id,
                password
        );
    }

    static ChangePasswordJpaEntity fromSnapshot(ChangePasswordSnapshot snapshot) {
        return new ChangePasswordJpaEntity(
                snapshot.id(),
                snapshot.password()
        );
    }
}
