package com.pi.kursy.courses.buy;

import com.pi.kursy.security.shared.RoleEnum;

record BuyCourseSnapshot(
        String id,
        Client client,
        Course course
) {

    record Client(
            String id,
            RoleEnum role
    ) { }

    record Course(
         String id,
         Teacher teacher
    ) {

        record Teacher(
                String id
        ) { }
    }
}
