package com.pi.kursy.users.register;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name = "users")
class ChangePasswordOrmEntity {
    @Id
    private String id;
    private String password;

    @PersistenceCreator
    protected ChangePasswordOrmEntity() {}

    private ChangePasswordOrmEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    ChangePasswordSnapshot toSnapshot() {
        return new ChangePasswordSnapshot(
                id,
                password
        );
    }

    static ChangePasswordOrmEntity fromSnapshot(ChangePasswordSnapshot snapshot) {
        return new ChangePasswordOrmEntity(
                snapshot.id(),
                snapshot.password()
        );
    }
}
