package com.pi.kursy.users.register;

import com.pi.kursy.users.shared.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name = "users")
class ChangePasswordOrmEntity {
    @Id
    private Long id;
    private String externalId;
    private String password;
    private UserStatus status;

    @PersistenceCreator
    protected ChangePasswordOrmEntity() {}

    private ChangePasswordOrmEntity(Long id, String externalId, String password, UserStatus status) {
        this.id = id;
        this.externalId = externalId;
        this.password = password;
        this.status = status;
    }

    ChangePasswordSnapshot toSnapshot() {
        return new ChangePasswordSnapshot(
                id,
                externalId,
                password,
                status
        );
    }

    static ChangePasswordOrmEntity fromSnapshot(ChangePasswordSnapshot snapshot) {
        return new ChangePasswordOrmEntity(
                snapshot.id(),
                snapshot.externalId(),
                snapshot.password(),
                snapshot.status()
        );
    }
}
