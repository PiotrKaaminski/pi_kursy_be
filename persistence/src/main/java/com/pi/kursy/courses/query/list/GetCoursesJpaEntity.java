package com.pi.kursy.courses.query.list;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "courses")
class GetCoursesJpaEntity {
    @Id
    private String id;
    private String name;
    private Float price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_category",
            joinColumns = { @JoinColumn(name = "course_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories;

    GetCoursesEntryDto toDto() {
        return new GetCoursesEntryDto(
                id,
                name,
                price,
                teacher.toDto(),
                categories.stream().map(Category::toDto).toList()
        );
    }


    @Entity
    @Table(name = "users")
    private static class Teacher {
        @Id
        private String id;
        private String username;

        private GetCoursesEntryDto.Teacher toDto() {
            return new GetCoursesEntryDto.Teacher(
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

        private GetCoursesEntryDto.Category toDto() {
            return new GetCoursesEntryDto.Category(
                    id,
                    name
            );
        }
    }
}
