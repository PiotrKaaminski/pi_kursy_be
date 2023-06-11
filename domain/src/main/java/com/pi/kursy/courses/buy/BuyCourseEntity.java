package com.pi.kursy.courses.buy;

import com.pi.kursy.security.shared.RoleEnum;

import java.util.UUID;

class BuyCourseEntity {
    private String id;
    private final Client client;
    private final Course course;

    private final BuyCourseRepository repository;

    BuyCourseEntity(Client client, Course course, BuyCourseRepository repository) {
        this.client = client;
        this.course = course;
        this.repository = repository;
    }

    BuyCourseSnapshot buy() throws Exception {
        validate();

        fillMissingFields();

        return toSnapshot();
    }

    private void validate() throws Exception {
        if (client.role.equals(RoleEnum.ADMIN)) {
            throw new Exception("Client with id " + client.id + " is admin and has access to all courses");
        }
        if (client.id.equals(course.teacher.id)) {
            throw new Exception("Client with id " + client.id + " is already a teacher of course with id " + course.id);
        }
        if (repository.accessExists(client.id, course.id)) {
            throw new Exception("Client with id " + client.id + " already has access to course with id " + course.id);
        }
    }

    private void fillMissingFields() {
        this.id = UUID.randomUUID().toString();
    }

    private BuyCourseSnapshot toSnapshot() {
        return new BuyCourseSnapshot(
                id,
                client.toSnapshot(),
                course.toSnapshot()
        );
    }

    static class Client {
        private final String id;
        private final RoleEnum role;

        private Client(String id, RoleEnum role) {
            this.id = id;
            this.role = role;
        }

        static Client fromSnapshot(BuyCourseSnapshot.Client snapshot) {
            return new Client(
                    snapshot.id(),
                    snapshot.role()
            );
        }

        private BuyCourseSnapshot.Client toSnapshot() {
            return new BuyCourseSnapshot.Client(
                    id,
                    role
            ) ;
        }
    }

    static class Course {
        private final String id;
        private final Teacher teacher;

        private Course(String id, Teacher teacher) {
            this.id = id;
            this.teacher = teacher;
        }

        static Course fromSnapshot(BuyCourseSnapshot.Course snapshot) {
            return new Course(
                    snapshot.id(),
                    Teacher.fromSnapshot(snapshot.teacher())
            );
        }

        private BuyCourseSnapshot.Course toSnapshot() {
            return new BuyCourseSnapshot.Course(
                    id,
                    teacher.toSnapshot()
            );
        }

        static class Teacher {
            private final String id;

            Teacher(String id) {
                this.id = id;
            }

            static Teacher fromSnapshot(BuyCourseSnapshot.Course.Teacher snapshot) {
                return new Teacher(
                        snapshot.id()
                );
            }

            private BuyCourseSnapshot.Course.Teacher toSnapshot() {
                return new BuyCourseSnapshot.Course.Teacher(
                        id
                );
            }
        }
    }
}
