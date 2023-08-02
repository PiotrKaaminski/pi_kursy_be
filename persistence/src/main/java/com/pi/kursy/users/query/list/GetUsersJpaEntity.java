package com.pi.kursy.users.query.list;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
class GetUsersJpaEntity {

    @Id
    private String id;
    private String username;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    GetUsersEntryDto toDto() {
        return new GetUsersEntryDto(
                id,
                username,
                role
        );
    }
}
