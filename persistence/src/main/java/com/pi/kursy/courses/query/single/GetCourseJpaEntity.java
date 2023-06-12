package com.pi.kursy.courses.query.single;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "courses")
class GetCourseJpaEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    private String description;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_course_access",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private Set<User> usersWithAccess;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Set<Section> sections;

    GetCourseSnapshot toSnapshot() {
        return new GetCourseSnapshot(
                id,
                name,
                price,
                description,
                user.toDto(),
                categories.stream().map(Category::toDto).toList(),
                sections.stream().map(Section::toDto).toList()
        );
    }


    @Entity
    @Table(name = "users")
    private static class User {
        @Id
        private String id;
        private String username;

        private GetCourseSnapshot.Teacher toDto() {
            return new GetCourseSnapshot.Teacher(
                    id,
                    username
            );
        }
    }

    @Entity
    @Table(name = "categories")
    private static class Category {
        @Id
        private String id;
        private String name;

        private GetCourseSnapshot.Category toDto() {
            return new GetCourseSnapshot.Category(
                    id,
                    name
            );
        }
    }

    @Entity
    @Table(name = "sections")
    private static class Section {
        @Id
        private String id;
        private String title;
        private Integer sequence;

        private GetCourseSnapshot.Section toDto() {
            return new GetCourseSnapshot.Section(
                    id,
                    title,
                    sequence
            );
        }
    }
}
