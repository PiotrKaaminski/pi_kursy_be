package com.pi.kursy.courses.buy;

import com.pi.kursy.security.shared.RoleEnum;
import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name = "user_course_access")
class BuyCourseJpaEntity {
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Client client;
    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @PersistenceCreator
    public BuyCourseJpaEntity() {}

    private BuyCourseJpaEntity(String id, Client client, Course course) {
        this.id = id;
        this.client = client;
        this.course = course;
    }

    static BuyCourseJpaEntity fromSnapshot(BuyCourseSnapshot snapshot) {
        return new BuyCourseJpaEntity(
                snapshot.id(),
                Client.fromSnapshot(snapshot.client()),
                Course.fromSnapshot(snapshot.course())
        );
    }

    @Entity
    @Table(name = "users")
    static class Client {
        @Id
        private String id;
        @Enumerated(EnumType.STRING)
        private RoleEnum role;

        @PersistenceCreator
        public Client() {}

        private Client(String id, RoleEnum role) {
            this.id = id;
            this.role = role;
        }

        BuyCourseSnapshot.Client toSnapshot() {
            return new BuyCourseSnapshot.Client(
                    id,
                    role
            );
        }

        private static Client fromSnapshot(BuyCourseSnapshot.Client snapshot) {
            return new Client(
                    snapshot.id(),
                    snapshot.role()
            );
        }
    }

    @Entity
    @Table(name = "courses")
    static class Course {
        @Id
        private String id;
        @OneToOne
        @JoinColumn(name = "teacher_id")
        private Teacher teacher;

        @PersistenceCreator
        public Course() {}

        private Course(String id, Teacher teacher) {
            this.id = id;
            this.teacher = teacher;
        }

        BuyCourseSnapshot.Course toSnapshot() {
            return new BuyCourseSnapshot.Course(
                    id,
                    teacher.toSnapshot()
            );
        }

        private static Course fromSnapshot(BuyCourseSnapshot.Course snapshot) {
            return new Course(
                    snapshot.id(),
                    Teacher.fromSnapshot(snapshot.teacher())
            );
        }

        @Entity
        @Table(name = "users")
        static class Teacher {
            @Id
            private String id;

            @PersistenceCreator
            public Teacher() {}

            private Teacher(String id) {
                this.id = id;
            }

            BuyCourseSnapshot.Course.Teacher toSnapshot() {
                return new BuyCourseSnapshot.Course.Teacher(
                        id
                );
            }

            private static Teacher fromSnapshot(BuyCourseSnapshot.Course.Teacher snapshot) {
                return new Teacher(
                        snapshot.id()
                );
            }
        }
    }
}
